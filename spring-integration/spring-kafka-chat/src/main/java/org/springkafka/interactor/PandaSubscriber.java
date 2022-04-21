package org.springkafka.interactor;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Component;
import org.springkafka.config.SpringKafkaProperties;
import org.springkafka.model.Panda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public record PandaSubscriber(
	ObjectMapper objectMapper,
	PollableChannel consumerChannel,
	KafkaProperties kafkaProperties,
	IntegrationFlowContext flowContext,
	SpringKafkaProperties springKafkaProperties,
	InMemoryStoreHandler inMemoryStoreHandler) {

	public List<Panda> read() {
		addAnotherListenerForTopics(springKafkaProperties.topic());

		return inMemoryStoreHandler.getPAYLOADS()
			.stream()
			.map(payload -> {
				try {
					return objectMapper.readValue(payload, Panda.class);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}).toList();
	}

	public void addAnotherListenerForTopics(String... topics) {
		Map<String, Object> consumerProperties = kafkaProperties.buildConsumerProperties();
		IntegrationFlow flow = IntegrationFlows
			.from(Kafka.messageDrivenChannelAdapter(
				new DefaultKafkaConsumerFactory<String, String>(consumerProperties), topics))
			.channel("inputChannel").get();
		this.flowContext.registration(flow).register();
	}
}
