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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConditionalOnProperty(value = "condition.enabled.redis", havingValue = "true")
public class RedisConfig {

  private static final String REDIS_PROTOCOL_PREFIX = "redis://";
  private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
  }

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
      .setPassword(redisProperties.getPassword())
      .setDatabase(redisProperties.getDatabase());

    return Redisson.create(config);
  }
}
