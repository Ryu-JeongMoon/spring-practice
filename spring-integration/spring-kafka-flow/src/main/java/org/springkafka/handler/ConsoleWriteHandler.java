package org.springkafka.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleWriteHandler implements MessageHandler {

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		log.info("message = {}", message.getPayload());
	}
}
