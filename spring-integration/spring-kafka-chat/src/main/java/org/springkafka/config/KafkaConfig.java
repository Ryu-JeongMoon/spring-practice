package org.springkafka.config;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.kafka.dsl.KafkaProducerMessageHandlerSpec;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.Message;
import org.springkafka.SpringKafkaApplication;
import org.springkafka.handler.ConsoleWriteHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableKafka
@EnableIntegration
@RequiredArgsConstructor
@EnableConfigurationProperties(SpringKafkaProperties.class)
@IntegrationComponentScan(basePackageClasses = SpringKafkaApplication.class)
public class KafkaConfig {

	private final DirectChannel fileProducerChannel;
	private final DirectChannel consoleProducerChannel;
	private final SpringKafkaProperties springKafkaProperties;

	@Bean
	public ProducerFactory<Integer, String> customProducerFactory() {
		Map<String, Object> configs = Map.of(
			ProducerConfig.LINGER_MS_CONFIG, 1,
			ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, springKafkaProperties.bootstrapServers(),
			ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
			ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
		);

		return new DefaultKafkaProducerFactory<>(configs);
	}

	@Bean
	public IntegrationFlow sendToKafkaFlow() {
		return flow -> flow
			.<String>split(p -> Stream.generate(() -> p).limit(101).iterator(), null)
			.publishSubscribeChannel(c -> c
				.subscribe(sf -> sf.handle(
					customKafkaMessageHandler(customProducerFactory(), springKafkaProperties.topic()),
					e -> e.id("kafkaProducer1")
				))
			);
	}

	@Bean
	public DefaultKafkaHeaderMapper mapper() {
		return new DefaultKafkaHeaderMapper();
	}

	private KafkaProducerMessageHandlerSpec<Integer, String, ?> customKafkaMessageHandler(
		ProducerFactory<Integer, String> producerFactory, String topic) {
		return Kafka
			.outboundChannelAdapter(producerFactory)
			.messageKey(m -> m
				.getHeaders()
				.get(IntegrationMessageHeaderAccessor.SEQUENCE_NUMBER))
			.headerMapper(mapper())
			.partitionId(m -> 10)
			.topicExpression("headers[kafka_topic] ?: '" + topic + "'")
			.configureKafkaTemplate(t -> t.id("kafkaTemplate:" + topic));
	}

	// AbstractMessageListenerContainer.AckMode.MANUAL -> ContainerProperties.AckMode.MANUAL 변경
	@Bean
	public IntegrationFlow fileWriterFlow() {
		log.info("[fileWriterFlow called]");

		return IntegrationFlows
			.from(fileProducerChannel)
			.filter(Message.class,
				m -> {
					String receivedMessageKey = m.getHeaders().get(KafkaHeaders.RECEIVED_MESSAGE_KEY, String.class);
					return receivedMessageKey == null || Integer.parseInt(receivedMessageKey) < 101;
				},
				f -> f.throwExceptionOnRejection(true))
			.<String, String>transform(String::toUpperCase)
			.handle(Files
				.outboundAdapter(new File("tmp/files"))
				.fileNameGenerator(message -> message.getPayload().toString().toLowerCase() + ".txt")
				.fileExistsMode(FileExistsMode.APPEND)
				.appendNewLine(true)
				.flushWhenIdle(false)
				.get())
			.get();
	}

	@Bean
	public IntegrationFlow consoleWriterFlow() {
		log.info("[consoleWriterFlow called]");

		return IntegrationFlows
			.from(consoleProducerChannel)
			.filter(Message.class,
				m -> {
					String messageKey = m.getHeaders().get(KafkaHeaders.RECEIVED_MESSAGE_KEY, String.class);
					return true;
				},
				f -> f.throwExceptionOnRejection(true))
			.<String[], String[]>transform(source -> Arrays.stream(source)
				.map(s -> s.toUpperCase()).toArray(String[]::new))
			.handle(new ConsoleWriteHandler())
			.get();
	}
}

/*
for producer =>
return IntegrationFlows
			.from(Kafka.messageDrivenChannelAdapter(
						consumerFactory,
						KafkaMessageDrivenChannelAdapter.ListenerMode.record,
						springKafkaProperties.topic()
					)
					.configureListenerContainer(c -> c.ackMode(ContainerProperties.AckMode.MANUAL)
						.id("testTopicListenerContainer"))
					.recoveryCallback(new ErrorMessageSendingRecoverer(errorChannel, new RawRecordHeaderErrorMessageStrategy()))
					.retryTemplate(new RetryTemplate())
					.filterInRetry(true)
			)
 */