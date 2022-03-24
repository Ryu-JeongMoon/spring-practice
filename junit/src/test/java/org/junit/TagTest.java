package org.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TagTest {

  private static final String FAST = "fast";
  private static final String SLOW = "slow";

  @Test
  @DisplayName("FAST 태그 달렸을 때만 돌리거라~!")
  @Tag(FAST)
  void onlyFastTag() {
    System.out.println("TagTest.onlyFastTag");
  }

  @Test
  @DisplayName("SLOW 태그 달렸을 때만 돌리거라~!")
  @Tag(SLOW)
  void onlySlowTag() throws InterruptedException {
    System.out.println("TagTest.onlySlowTag");
    Thread.sleep(100);
  }
}

/*
@Tag 주로 로컬 환경과 CI 환경 나누기 위해 사용
인텔리제이 내에서만 돌릴 때는 Edit Configuration 통해 해결 가능하지만
CLI 환경에서는 번거로운 설정이 들어가야 하고 maven-plugin 까지 써야하기 때문에 귀찮음
@Enabled, @Disabled 쓰는게 나아보임
 */