package org.springkafka.handler;

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

	/**
	 * MessageHandler 의 handleMessage 메서드는 메세지 수신할 때마다 처리된다<br/>
	 * InMemoryStoreHandler 예에서는 받아서 in-memory list 에 넣어주는 작업만 하게 된다
	 */
	@Override
	public void handleMessage(@NotNull Message<?> message) throws MessagingException {
		log.info("received message='{}'", message);

		HEADERS.add(message.getHeaders().toString());
		PAYLOADS.add(message.getPayload().toString());
	}
}
