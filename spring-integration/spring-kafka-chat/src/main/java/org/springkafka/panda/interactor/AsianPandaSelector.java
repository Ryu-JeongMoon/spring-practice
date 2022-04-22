package org.springkafka.panda.interactor;

import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springkafka.panda.model.Panda;

@Component
public class AsianPandaSelector implements MessageSelector {

	@Override
	public boolean accept(Message<?> message) {
		return ((Panda)message.getPayload()).getBirthPlace() == Panda.BirthPlace.ASIA;
	}
}
