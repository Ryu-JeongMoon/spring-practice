package com.springanything.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.support.RetryTemplate;

@EnableRetry
@Configuration
@ConditionalOnProperty(name = "condition.enabled.retry", havingValue = "true")
public class RetryConfig {

  @Bean
  public RetryTemplate retryTemplate() {
    return RetryTemplate.builder()
      .maxAttempts(1)
      .retryOn(Exception.class)
      .exponentialBackoff(
        ExponentialBackOffPolicy.DEFAULT_INITIAL_INTERVAL,
        ExponentialBackOffPolicy.DEFAULT_MULTIPLIER,
        ExponentialBackOffPolicy.DEFAULT_MAX_INTERVAL)
      .build();
  }

  @Bean
  public RetryOperationsInterceptor retryInterceptor() {
    var interceptor = new RetryOperationsInterceptor();
    interceptor.setRetryOperations(retryTemplate());
    return interceptor;
  }
}
