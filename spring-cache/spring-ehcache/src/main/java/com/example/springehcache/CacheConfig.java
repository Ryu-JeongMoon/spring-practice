package com.example.springehcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableCaching
public class CacheConfig implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(CacheConfig.class);

	private final CacheManager cacheManager;

	public CacheConfig(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cmfb.setShared(true);
		return cmfb;
	}

	@Override
	public void run(String... args) {
		log.info("\n\n=========================================================\n");
		log.info("Using cache manager: {}\n", this.cacheManager.getClass().getName());
		log.info("=========================================================\n\n");
	}
}
