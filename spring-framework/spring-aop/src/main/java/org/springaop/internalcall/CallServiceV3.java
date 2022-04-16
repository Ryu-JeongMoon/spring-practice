package org.springaop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

  private final InternalService internalService;

  public void external() {
    log.info("call external");
    internalService.internal();
  }

}

/*
대안3. 구조 변경

내부에 있던 internal() 메서드를 분리해버린다
일반적으로 AOP는 트랜잭션, 로깅 목적으로 사용되는데 짜잘한 메서드에 붙이지 않고 큼지막한 단위로 붙이게 된다
로그를 적용시킬 만큼 큼지막한 단위의 메서드기에 분리하는 편이 자연스러운 구조가 될 수도 있다

다만 구조적으로 같이 있는게 합리적인 경우에는
1. 대안2.를 참조해 지연 조회 때린다
2. Aspectj Compiler 로 바이트 코드에다가 AOP 코드를 붙여버리는 식으로 처리한다
3. 내부 호출하지 말고 클라이언트 측에서 external(), internal()을 연쇄적으로 호출하도록 바꾼다
 */