package com.springanything.retry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RetryController {

  private final RetryService retryService;

  @GetMapping("/retry")
  public String retry() {
    return retryService.retry();
  }
}
