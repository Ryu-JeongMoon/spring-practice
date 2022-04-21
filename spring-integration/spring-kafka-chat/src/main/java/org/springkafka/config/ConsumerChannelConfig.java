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
import org.springkafka.interactor.InMemoryStoreHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SpringKafkaProperties.class)
public class ConsumerChannelConfig {

	private final SpringKafkaProperties springKafkaProperties;

	@Bean
	public PollableChannel consumerChannel() {
		return new QueueChannel();
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

	@Bean
	@SuppressWarnings("unchecked")
	public ConcurrentMessageListenerContainer<String, String> kafkaListenerContainer() {
		return (ConcurrentMessageListenerContainer<String, String>)
			new ConcurrentMessageListenerContainer<>(
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
