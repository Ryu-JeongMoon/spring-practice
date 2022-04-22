package org.springkafka.panda.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springkafka.config.SpringKafkaProperties;
import org.springkafka.panda.interactor.PandaPublisher;
import org.springkafka.panda.interactor.PandaSubscriber;
import org.springkafka.panda.model.Panda;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@EnableConfigurationProperties(SpringKafkaProperties.class)
public class PandaController {

	private final DirectChannel producerChannel;
	private final PandaPublisher pandaPublisher;
	private final PandaSubscriber pandaSubscriber;
	private final SpringKafkaProperties springKafkaProperties;

	@GetMapping("/panda")
	public Mono<Panda> getPandaRandomly() {
		log.info("[called] PandaController.getPandaRandomly");

		Panda panda = pandaPublisher.getRandomPanda();

		Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.TOPIC, springKafkaProperties.topic());
		producerChannel.send(new GenericMessage<>(panda, headers));

		return Mono.just(panda);
	}

	@GetMapping("/read")
	public Flux<List<Panda>> read() {
		return Flux.just(pandaSubscriber.read())
	}
}
