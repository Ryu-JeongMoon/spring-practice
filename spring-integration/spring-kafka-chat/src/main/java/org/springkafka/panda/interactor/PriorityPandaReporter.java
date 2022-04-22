package org.springkafka.panda.interactor;

import org.springframework.integration.channel.PriorityChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springkafka.panda.model.Panda;

@Component
public class PriorityPandaReporter extends PandaReporter {

	public PriorityPandaReporter(PriorityChannel pandaChannel) {
		super(pandaChannel);
	}

	@Override
	public void sendPanda(Panda panda) {
		pandaChannel.send(new GenericMessage<>(panda));
	}
}
