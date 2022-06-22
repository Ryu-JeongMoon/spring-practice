package com.example.springehcache;

import java.util.concurrent.TimeUnit;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ModifiedExpiryPolicy;
import javax.cache.spi.CachingProvider;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JCacheTest {

	CachingProvider provider = Caching.getCachingProvider();
	CacheManager manager = provider.getCacheManager();

	@BeforeEach
	void setUp() {
		manager.destroyCache("test");
	}

	protected void checkCache(Cache cache) throws InterruptedException {
		final String key = "test";
		Assertions.assertNull(cache.get(key), "Cache should initially be empty");
		cache.put(key, new Object());
		Assertions.assertNotNull(cache.get(key), "Should not have timed out yet");
		Thread.sleep(2000);
		Assertions.assertNull(cache.get(key), "Should have timed out by now");
	}

	@Test
	public void testJCacheManuallyConfigured() throws InterruptedException {
		CachingProvider provider = Caching.getCachingProvider();
		CacheManager manager = provider.getCacheManager();
		Cache cache = manager.createCache("myManuallyConfiguredCache",
			new MutableConfiguration()
				.setStoreByValue(false)
				.setExpiryPolicyFactory(
					ModifiedExpiryPolicy.factoryOf(new Duration(TimeUnit.MILLISECONDS, 1000))
				));
		checkCache(cache);
	}

	@Test
	public void testJCacheXmlConfigured() throws InterruptedException {
		final String cacheName = "cache123";
		Cache cache = manager.getCache(cacheName);
		checkCache(cache);
	}
}
