package com.springanything.cache;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CacheController {

  private final CacheService cacheService;

  @GetMapping("/test/cache")
  public String get(@RequestParam String key) {
    return cacheService.get(key);
  }

  @GetMapping("/test/caches")
  public Collection<String> getMultiples(@RequestParam String key) {
    return cacheService.getMultiples(key);
  }
}
