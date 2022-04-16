package org.springaop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {

  //  private final ApplicationContext applicationContext;
  private final ObjectProvider<CallServiceV2> objectProvider;

  public void external() {
    log.info("call external");
    CallServiceV2 callService = objectProvider.getObject();
    callService.internal();
  }

  public void internal() {
    log.info("call internal");
  }
}

/*
대안2
- ApplicationContext 를 사용한 지연 조회
- ObjectProvider 를 사용한 지연 조회

ApplicationContext 는 거대한 덩어리기 때문에 무엇을 사용하는지 명확하지 않다
대신 ObjectProvider 를 사용해 객체 제공 용도임을 드러낸다
 */