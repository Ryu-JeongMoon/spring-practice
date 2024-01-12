package com.springanything.retry;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RetryService {

  private final AtomicBoolean isPassed = new AtomicBoolean(true);

  public void retry() {
    if (isPassed.getAndSet(false)) {
      throw new RuntimeException("retry");
    }

    log.info("PASSED");
  }
}
