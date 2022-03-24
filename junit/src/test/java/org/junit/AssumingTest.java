package org.junit;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

public class AssumingTest {

  Environment environment = new StandardEnvironment();

  @Test
  @DisplayName("실행 환경 조건이 맞을 때만 돌거라")
  void assume() {
    assumeTrue(
      Arrays.stream(environment.getDefaultProfiles())
        .anyMatch(profile -> profile.equalsIgnoreCase("default"))
    );

    System.out.println("AssumingTest.assume");
  }

  @Test
  @DisplayName("맥이랑 리눅스일 때만 돌거라~~ 🐬")
  @EnabledOnOs({OS.MAC, OS.LINUX})
  void enableWithOs() {
    System.out.println("AssumingTest.enableWithOs");
  }

  @Test
  @DisplayName("맥이랑 리눅스일 때 돌지 말거라~~ 🐬")
  @DisabledOnOs({OS.MAC, OS.LINUX})
  void disableWithOs() {
    System.out.println("AssumingTest.disableWithOs");
  }

  @Test
  @DisplayName("환경변수에 KEY 값이 있을 때, VALUE 일치하면 돌리거라")
  @EnabledIfEnvironmentVariable(named = "KEY", matches = "VALUE")
  void yahoo() {
    System.out.println("AssumingTest.yahoo");
  }

  @Test
  @DisplayName("자바 17일 때만 돌거라! 🤬")
  @EnabledOnJre(JRE.JAVA_17)
  void enableWithSpecificJre() {
    System.out.println("AssumingTest.enableWithSpecificJre");
  }
}

/*
EnabledOnOs, OS 별 특화 테스트 코드 작성 가능!?
EnabledOnJre, 특정 자바 버전만 돌아가도록 작성 가능?!
 */