package com.springanything.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "webclient")
public record WebClientProperties(
  int maxByteCount,
  int connectTimeoutMillis,
  int responseTimeoutSeconds,
  int readWriteTimeoutSeconds,
  int maxIdleAndLifeTimeSeconds
) {

}
