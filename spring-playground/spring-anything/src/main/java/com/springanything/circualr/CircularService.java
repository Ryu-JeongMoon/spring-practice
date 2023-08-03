package com.springanything.circualr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CircularService {

  @Lazy
  @Autowired
  private CircularService self;

  @Cacheable("self-invocation")
  public String get() {
    log.info("is it called twice?");
    return "self-invocation";
  }

  /**
   * it uses cache by self-invocation.
   */
  public String getBySelf() {
    return self.get();
  }
}
