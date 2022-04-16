package org.springaop.internalcall;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springaop.internalcall.aop.CallLogAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV1Test {

  @Autowired
  CallServiceV1 callService;

  @Test
  @DisplayName("")
  void external() {
    callService.external();
  }
}