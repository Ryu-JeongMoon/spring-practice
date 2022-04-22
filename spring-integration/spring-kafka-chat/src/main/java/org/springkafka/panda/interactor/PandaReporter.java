package org.springkafka.panda.interactor;

import org.springframework.integration.channel.PriorityChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springkafka.panda.model.Panda;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PandaReporter {

	protected final PriorityChannel pandaChannel;

	public void sendPanda(Panda panda) {
		pandaChannel.send(new GenericMessage<>(panda));
		log.info("Panda Sent {}", panda);
	}
}
