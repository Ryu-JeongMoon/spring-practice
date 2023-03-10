package com.springanything.cache;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

	// outer::inner::#key 계층형으로 저장
	@Cacheable(value = "outer::inner", key = "#key")
	public String get(String key) {
		return key + " cache";
	}

	// outer::#key, inner::#key 두개의 키로 저장
	@Cacheable(value = { "outer", "inner" }, key = "#key")
	public Collection<String> getMultiples(String key) {
		return key.chars()
			.mapToObj(i -> (char) i + " caches")
			.toList();
	}
}
