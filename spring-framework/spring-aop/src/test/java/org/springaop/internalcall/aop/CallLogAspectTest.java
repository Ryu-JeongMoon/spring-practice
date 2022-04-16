package org.springaop.internalcall.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springaop.internalcall.CallServiceV0;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
class CallLogAspectTest {

  @Autowired
  CallServiceV0 callService;

  @Test
  @DisplayName("프록시 내에서 내부 호출이 일어난다면 AOP 적용되지 않음")
  void external() {
    callService.external();
  }

  @Test
  @DisplayName("")
  void internal() {
    callService.internal();
  }
}

/*
external 호출 시에는 AOP 적용되나
internal 호출 시에 this.internal() 로 호출하는데
this는 암묵적으로 자기 자신의 인스턴스를 가르킨다
즉 프록시를 거쳐가야 AOP 적용되는데 직접 인스턴스 호출하여 거치지 않은 것
 */