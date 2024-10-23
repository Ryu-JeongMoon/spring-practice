package com.springanything.cache;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@ConditionalOnBean(RedissonClient.class)
public class RedissonController {

  private final RedissonClient redisson;

  @GetMapping("/test/redisson/{id}")
  public String get(@PathVariable String id) {
    RLock lock = redisson.getLock("test");
    try {
      boolean acquired = lock.tryLock(10, TimeUnit.SECONDS);
      if (acquired) {
        log.info("lock acquired : {}", id);
        Thread.sleep(2000);
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      lock.unlock();
    }

    return "hello world!";
  }
}
