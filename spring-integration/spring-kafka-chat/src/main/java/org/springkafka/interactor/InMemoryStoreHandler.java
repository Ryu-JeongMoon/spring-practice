package org.springkafka.interactor;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class InMemoryStoreHandler implements MessageHandler {

	private final List<String> PAYLOADS = new ArrayList<>();
	private final List<String> HEADERS = new ArrayList<>();

	@Override
	public void handleMessage(@NotNull Message<?> message) throws MessagingException {
		log.info("received message='{}'", message);

		HEADERS.add(message.getHeaders().toString());
		PAYLOADS.add(message.getPayload().toString());
	}
}
