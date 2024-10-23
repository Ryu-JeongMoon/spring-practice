package com.springanything.cache;

import java.util.Optional;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@ConditionalOnProperty(value = "conditional.enabled.redisson", havingValue = "true")
public class RedissonConfig {

  private static final String REDIS_PROTOCOL_PREFIX = "redis://";
  private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

  @Bean
  public RedissonClient redissonClient(RedisProperties redisProperties) {
    String prefix = redisProperties.getSsl().isEnabled()
      ? REDISS_PROTOCOL_PREFIX
      : REDIS_PROTOCOL_PREFIX;

    int timeout = Optional.ofNullable(redisProperties.getTimeout())
      .map(x -> (int) x.toMillis())
      .orElse(10000);

    Config config = new Config();
    SingleServerConfig singleServerConfig = config.useSingleServer();

    singleServerConfig.setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
      .setConnectTimeout(timeout)
      .setDatabase(redisProperties.getDatabase());

    if (StringUtils.hasText(redisProperties.getPassword())) {
      singleServerConfig.setPassword(redisProperties.getPassword());
    }

    return Redisson.create(config);
  }

}
