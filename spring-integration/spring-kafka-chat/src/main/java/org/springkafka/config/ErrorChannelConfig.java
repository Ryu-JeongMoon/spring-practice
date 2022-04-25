package org.springkafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.stereotype.Component;

@Component
public class ErrorChannelConfig {

	@Bean
	public PollableChannel errorChannel() {
		return new QueueChannel();
	}
}
