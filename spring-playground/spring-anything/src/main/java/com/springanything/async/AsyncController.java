package com.springanything.async;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AsyncController {

  private final AsyncService asyncService;

  @GetMapping("/async/send")
  public Void send() {
    int length = 30;
    CompletableFuture<?>[] futures = new CompletableFuture[length];
    for (int i = 0; i < length; i++) {
      futures[i] = asyncService.send(i);
    }
    return CompletableFuture.allOf(futures).join();
  }
}
