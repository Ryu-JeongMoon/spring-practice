package com.springanything.retry;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractIntegrationTest;

class RetryServiceTest extends AbstractIntegrationTest {

  @Autowired
  private RetryService retryService;

  @DisplayName("retry 에 의해 예외가 발생하지 않고 2번째 응답으로 정상 응답을 받는다.")
  @Test
  void retry() {
    assertDoesNotThrow(() -> retryService.retry());
  }
}
