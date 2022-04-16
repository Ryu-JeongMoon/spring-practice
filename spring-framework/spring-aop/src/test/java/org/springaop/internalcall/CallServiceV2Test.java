package org.springaop.internalcall;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springaop.internalcall.aop.CallLogAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV2Test {

  @Autowired
  CallServiceV2 callService;

  @Test
  @DisplayName("")
  void external() {
    callService.external();
  }
}