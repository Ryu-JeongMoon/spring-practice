package org.springkafka.panda.interactor;

import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Component;
import org.springkafka.config.SpringKafkaProperties;
import org.springkafka.handler.InMemoryStoreHandler;
import org.springkafka.panda.model.Panda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public record PandaSubscriber(
	ObjectMapper objectMapper,
	PollableChannel consumerChannel,
	DirectChannel producerChannel,
	KafkaProperties kafkaProperties,
	IntegrationFlowContext flowContext,
	SpringKafkaProperties springKafkaProperties,
	InMemoryStoreHandler inMemoryStoreHandler) {

	/**
	 * PollableChannel 의 receive 메서드는 인자가 있는 것과 없는 것으로 나뉜다<br/>
	 * 있는 경우 -> timeout 걸고 큐에서 메세지를 가져오고<br/>
	 * 없는 경우 -> 큐 상태와 관계 없이 땡겨오는데 큐가 비어있다면 busy-waiting 에 걸릴 수 있다<br/>
	 */
	public List<Panda> read() {
		addAnotherListenerForTopics(springKafkaProperties.topic());

		return inMemoryStoreHandler.getPAYLOADS()
			.stream()
			.peek(message -> log.info("message = {}", message))
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
			.channel("memoryConsumerChannel").get();
		this.flowContext.registration(flow).register();
	}
}
