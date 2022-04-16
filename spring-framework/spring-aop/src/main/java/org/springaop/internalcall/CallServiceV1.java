package org.springaop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

  private CallServiceV1 callServiceV1;

  @Autowired
  public void setCallService(CallServiceV1 callServiceV1) {
    log.info("callService.getClass() = {}", callServiceV1.getClass());
    this.callServiceV1 = callServiceV1;
  }

  public void external() {
    log.info("call external");
    callServiceV1.internal();
  }

  public void internal() {
    log.info("call internal");
  }
}

/*
대안1. 자기 자신 주입
순환 참조 문제로 인해 사용하지 말아야 한다

자기 자신을 주입 받아야 할 필요가 있을 때
생성이 끝나지 않은 시점에 주입 받으려 하기 때문에 생성자 주입을 사용할 수 없다

대안1.에서는 프록시로 생성되어 있는 자기 자신을 주입 받는다
내부호출이었던 internal() -> callService.internal()로 외부 호출로 바꿔버린다

부트 최신 버전부터 기본적으로 순환 참조가 불가능하기에
spring.main.allow-circular-references=true 설정을 해줘야 한다
AOP 적용하려고 순환 참조 안정성이 깨지게 되는 문제가 발생하는 것
 */