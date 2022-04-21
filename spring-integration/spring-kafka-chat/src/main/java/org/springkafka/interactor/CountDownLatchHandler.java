package org.springkafka.interactor;

import java.util.concurrent.CountDownLatch;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CountDownLatchHandler implements MessageHandler {

	private final CountDownLatch latch = new CountDownLatch(10);

	public CountDownLatch getLatch() {
		return latch;
	}

	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		log.info("received message='{}'", message);
		latch.countDown();
	}
}
