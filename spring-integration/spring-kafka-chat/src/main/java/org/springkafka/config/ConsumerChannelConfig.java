package org.springkafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.messaging.PollableChannel;
import org.springkafka.handler.InMemoryStoreHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SpringKafkaProperties.class)
public class ConsumerChannelConfig {

	private final SpringKafkaProperties springKafkaProperties;

	/**
	 * PollableChannel 사용 시 기본 생성자를 사용하면 Integer.MAX_VALUE 로 큐를 생성해버린다<br/>
	 * 따라서 capacity 지정해주는 생성자를 사용해 큐 생성해야 한다
	 */
	@Bean
	public PollableChannel consumerChannel() {
		return new QueueChannel(50);
	}

	// @Bean
	// @ServiceActivator(inputChannel = "consumerChannel")
	// public CountDownLatchHandler countDownLatchHandler() {
	// 	return new CountDownLatchHandler();
	// }

	@Bean
	@ServiceActivator(inputChannel = "consumerChannel")
	public InMemoryStoreHandler inMemoryStoreHandler() {
		return new InMemoryStoreHandler();
	}

	@Bean
	public KafkaMessageDrivenChannelAdapter<String, String> kafkaMessageDrivenChannelAdapter() {
		KafkaMessageDrivenChannelAdapter<String, String> adapter =
			new KafkaMessageDrivenChannelAdapter<>(kafkaListenerContainer());
		adapter.setOutputChannel(consumerChannel());

		return adapter;
	}

	/**
	 * ConsumerFactory<K, V>로 인자를 넣어 받아오는데 <?, ?>로 설정되어 있기 때문에<br/>
	 * Type Parameter Capturing 안 되는 문제가 있어 명시적으로 타입 캐스팅을 해줘야한다<br/>
	 * IDE 에서는 redundant cast 라 하지만 없으면 구체화할 수 없기 때문에 컴파일 에러 뜸
	 */
	@Bean
	@SuppressWarnings("unchecked")
	public ConcurrentMessageListenerContainer<String, String> kafkaListenerContainer() {
		return new ConcurrentMessageListenerContainer<>(
			consumerFactory(),
			new ContainerProperties(springKafkaProperties.topic())
		);
	}

	@Bean
	public ConsumerFactory<?, ?> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, springKafkaProperties.bootstrapServers());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "spring-integration-kafka");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return properties;
	}
}

