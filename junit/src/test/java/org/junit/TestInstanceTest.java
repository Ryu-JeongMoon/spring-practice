package org.junit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class TestInstanceTest {

  int value = 0;

  @Disabled
  @Test
  @DisplayName("확인 - 1")
  void checkFirst() {
    value++;
    System.out.println("value = " + value);
  }

  @Test
  @DisplayName("확인 - 2")
  void checkSecond() {
    value++;
    System.out.println("value = " + value);
  }
}

/*
JUnit 기본 전략은 테스트 메서드 당 인스턴스를 새로 만든다
인스턴스 새로 생성하기 위한 오버헤드가 있지만 테스트 간 의존성을 없애주기 때문
그렇기에 각각 ++ 연산을 때리고 확인해봐도 같은 값이 확인 된다

@TestInstance(Lifecycle.PER_CLASS) 주고 돌리면 모든 테스트에 대해 하나의 인스턴스만 생성한다
성능 상 유리한 점이 있고 인스턴스가 단 하나만 만들어지기 때문에
@BeforeAll, @AfterAll 이 static method 아니어도 된다!
 */