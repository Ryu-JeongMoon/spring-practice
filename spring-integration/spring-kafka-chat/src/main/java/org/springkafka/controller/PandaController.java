package org.springkafka.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springkafka.interactor.PandaSubscriber;
import org.springkafka.model.Panda;
import org.springkafka.interactor.PandaPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PandaController {

	private final PandaPublisher pandaPublisher;
	private final DirectChannel producerChannel;
	private final PandaSubscriber pandaSubscriber;

	@GetMapping("/panda")
	public Mono<Panda> getPandaRandomly() {
		log.info("[called] PandaController.getPandaRandomly");

		Panda panda = pandaPublisher.getRandomPanda();

		Map<String, Object> headers = Collections.singletonMap(KafkaHeaders.TOPIC, panda.getBirthPlace().name());
		producerChannel.send(new GenericMessage<>(panda, headers));

		return Mono.just(panda);
	}

	@GetMapping("/read")
	public Mono<Void> read() {
		pandaSubscriber.read();
		return Mono.empty();
	}

	@GetMapping("/subscribe")
	public void subscribe() {

	}
}
