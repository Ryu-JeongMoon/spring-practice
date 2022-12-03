package com.example.springehcache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = { "trans" })
public class MyPandaService {

	private static final List<Panda> PANDAS = new ArrayList<>();

	private final CacheManager cacheManager;

	public MyPandaService(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@CachePut
	public void addTransaction(Panda panda) {
		PANDAS.add(panda);
	}

	@CacheEvict(allEntries = true)
	public void deleteAll() {
		PANDAS.clear();
	}
}
