package com.springanything.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "webclient")
public record WebClientProperties(
	int maxByteCount,
	int connectTimeoutMillis,
	int responseTimeoutSeconds,
	int readWriteTimeoutSeconds,
	int maxIdleAndLifeTimeSeconds
) {

}
