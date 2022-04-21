package org.springkafka.interactor;

import java.util.Map;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Component;
import org.springkafka.config.SpringKafkaProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public record PandaSubscriber(
	PollableChannel consumerChannel,
	KafkaProperties kafkaProperties,
	IntegrationFlowContext flowContext,
	SpringKafkaProperties springKafkaProperties) {

	public void read() {
		addAnotherListenerForTopics(springKafkaProperties.topic());

		Message<?> message = consumerChannel.receive();
		while (message != null) {
			log.info("[received] {}", message);
			message = consumerChannel.receive();
		}
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
