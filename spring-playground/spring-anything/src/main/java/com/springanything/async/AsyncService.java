package com.springanything.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AsyncService {

  @Async
  public CompletableFuture<Void> send(int count) {
    log.info("Sending message: {}", count);
    return CompletableFuture.completedFuture(null);
  }
}
