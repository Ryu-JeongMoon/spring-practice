package com.springanything.cache;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

  private final RedisProperties redisProperties;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    return jedis();
  }

  private RedisConnectionFactory jedis() {
    RedisStandaloneConfiguration redisStandaloneConfig = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
    JedisClientConfiguration clientConfig = JedisClientConfiguration
      .builder()
      .usePooling()
      .build();
    return new JedisConnectionFactory(redisStandaloneConfig, clientConfig);
  }

  // public RedisConnectionFactory lettuce() {
  // 	RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
  // 	configuration.setPassword(RedisPassword.none());
  // 	return new LettuceConnectionFactory(configuration);
  // }

  @Bean
  public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
    stringRedisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    return stringRedisTemplate;
  }

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    return RedisCacheManager.builder(redisConnectionFactory).build();
  }
}
