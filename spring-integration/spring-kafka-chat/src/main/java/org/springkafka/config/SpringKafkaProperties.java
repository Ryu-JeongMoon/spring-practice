package org.springkafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("spring.kafka")
public record SpringKafkaProperties(String topic, String bootstrapServers) {

}
