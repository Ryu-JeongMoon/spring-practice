package org.springkafka.config;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.MessageHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SpringKafkaProperties.class)
public class ProducerChannelConfig {

	private final SpringKafkaProperties kafkaProperties;

	@Bean
	public DirectChannel fileProducerChannel() {
		return new DirectChannel();
	}

	@Bean
	public DirectChannel consoleProducerChannel() {
		return new DirectChannel();
	}

	@Bean
	@ServiceActivator(inputChannel = "memoryProducerChannel")
	public MessageHandler kafkaMessageHandler() {
		KafkaProducerMessageHandler<String, String> handler = new KafkaProducerMessageHandler<>(kafkaTemplate());
		handler.setMessageKeyExpression(new LiteralExpression("spring-integration-kafka"));
		return handler;
	}

	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> configs = Map.of(
			ProducerConfig.LINGER_MS_CONFIG, 1,
			ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrapServers(),
			ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
			ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
		);
		return new DefaultKafkaProducerFactory<>(configs);
	}
}
