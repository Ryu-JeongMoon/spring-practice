package com.springanything.retry;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryService {

  private final AtomicBoolean isPassed = new AtomicBoolean(true);

  @Retryable
  public String retry() {
    if (isPassed.getAndSet(false)) {
      throw new RuntimeException("retry");
    }

    isPassed.getAndSet(true);
    return "PASSED";
  }
}
