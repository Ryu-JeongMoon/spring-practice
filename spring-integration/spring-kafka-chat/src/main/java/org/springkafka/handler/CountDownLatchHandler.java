package org.springkafka.handler;

import java.util.concurrent.CountDownLatch;

import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CountDownLatchHandler implements MessageHandler {

	private final CountDownLatch latch = new CountDownLatch(10);

	public CountDownLatch getLatch() {
		return latch;
	}

	@Override
	public void handleMessage(@NotNull Message<?> message) throws MessagingException {
		log.info("received message='{}'", message);
		latch.countDown();
	}
}
