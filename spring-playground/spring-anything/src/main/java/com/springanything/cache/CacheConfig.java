package com.springanything.cache;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
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
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
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
		return redisson();
	}

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		config.useSingleServer()
			.setAddress(String.format("redis://%s:%d", "localhost", 6379))
			.setTimeout(10_000);
		return Redisson.create(config);
	}

	private RedissonConnectionFactory redisson() {
		return new RedissonConnectionFactory(redissonClient());
	}

	private RedisConnectionFactory jedis() {
		RedisStandaloneConfiguration redisStandaloneConfig = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
		JedisClientConfiguration clientConfig = JedisClientConfiguration
			.builder()
			.usePooling()
			.build();
		return new JedisConnectionFactory(redisStandaloneConfig, clientConfig);
	}

	public RedisConnectionFactory lettuce() {
		return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
		stringRedisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
		return stringRedisTemplate;
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return RedisCacheManager.builder(redisConnectionFactory).build();
	}
}
