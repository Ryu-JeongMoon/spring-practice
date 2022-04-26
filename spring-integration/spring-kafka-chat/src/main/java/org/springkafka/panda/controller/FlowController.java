package org.springkafka.panda.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springkafka.config.SpringKafkaProperties;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class FlowController {

	private final DirectChannel fileProducerChannel;
	private final DirectChannel consoleProducerChannel;
	private final SpringKafkaProperties springKafkaProperties;

	@GetMapping("/flow")
	public Mono<String> flowYahoo(@RequestParam String payload) {
		Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.TOPIC, springKafkaProperties.topic());
		fileProducerChannel.send(new GenericMessage<>(payload, headers), 1000);

		return Mono.just("%s ok~~".formatted(payload));
	}

	@GetMapping("/console")
	public Flux<String> consoleFlow(@RequestParam String... payloads) {
		Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.TOPIC, springKafkaProperties.topic());
		consoleProducerChannel.send(new GenericMessage<>(payloads, headers), 1000);

		return Flux.just("%s ok~~".formatted(Arrays.toString(payloads)));
	}
}
