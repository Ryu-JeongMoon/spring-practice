package org.junit;

import org.junit.annotation.SlowTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

//@ExtendWith(FindSlowTestExtension.class)
public class FindSlowTest {

  @RegisterExtension
  FindSlowTestExtension findSlowTestExtensionOver1000L = new FindSlowTestExtension(1000L);

  @Test
  @DisplayName("@SlowTest 붙일 놈")
  void possibleSlowTest() throws InterruptedException {
    Thread.sleep(1002);
    System.out.println("FindSlowTest.possibleSlowTest");
  }

  @Test
  @DisplayName("@SlowTest 붙일 놈")
  void possibleSlowTestOver1500L() throws InterruptedException {
    Thread.sleep(1501L);
    System.out.println("FindSlowTest.possibleSlowTest");
  }

  @SlowTest
  @DisplayName("Extension 감지 안 됨")
  void slowTest() throws InterruptedException {
    Thread.sleep(1002);
    System.out.println("FindSlowTest.possibleSlowTest");
  }
}

/*
@ExtendWith 사용하여 등록하면 간편하고 좋지만 커스텀할 방법이 없다

@RegisterExtension 사용하여 직접 인자 주는 방식으로 커스텀 가능
여러 개 만드는 건 불가능한 듯?!
 */