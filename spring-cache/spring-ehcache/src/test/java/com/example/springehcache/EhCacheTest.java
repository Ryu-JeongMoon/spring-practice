package com.example.springehcache;

import javax.cache.Caching;

import org.ehcache.jcache.JCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheTest {

	private final String cacheName = "cache123";

	@BeforeEach
	void setUp() {
		CacheManager cacheManager = CacheManager.newInstance();
		cacheManager.clearAll();

		Cache cache = cacheManager.getCache(cacheName);
		cache.put(new Element("cache-key", "cache-value"));
	}

	private void checkCache(Cache cache) throws InterruptedException {
		Assertions.assertNull(cache.get("test"), "Cache should initially be empty");
		cache.put(new Element("test", new Object()));
		Assertions.assertNotNull(cache.get("test"), "Should not have timed out");
		Thread.sleep(2000);
		Assertions.assertNull(cache.get("test"), "Should have timed out");
	}

	@Test
	public void testEhCacheDirect() throws InterruptedException {
		CacheManager manager = CacheManager.newInstance();
		Cache cache = manager.getCache(cacheName);
		checkCache(cache);
	}

	@Test
	public void testEhCacheFromJCache() throws InterruptedException {
		JCache jcache = Caching.getCachingProvider().getCacheManager().getCache(cacheName).unwrap(JCache.class);
		Cache cache = (Cache)jcache.unwrap(Cache.class);
		checkCache(cache);
	}

	@Test
	public void testEhCacheTimeToLive() throws InterruptedException {
		Thread.sleep(2_500);
		CacheManager manager = CacheManager.newInstance();
		Cache cache = manager.getCache(cacheName);
		Element element = cache.get("cache-key");
		System.out.println("element = " + element);
	}
}
