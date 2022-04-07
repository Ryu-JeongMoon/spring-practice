package org.junit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class RepeatTest {

  private static final String NAME_FOR_REPEATED_TEST = "{displayName} => {currentRepetition} / {totalRepetitions}";

  @DisplayName("반복하거라 😡")
  @RepeatedTest(value = 3,
    name = RepeatedTest.DISPLAY_NAME_PLACEHOLDER + " => " +
      RepeatedTest.CURRENT_REPETITION_PLACEHOLDER + " / " +
      RepeatedTest.TOTAL_REPETITIONS_PLACEHOLDER)
  void repeatWithConstant() {
    System.out.println("RepeatTest.repeat");
  }

  @DisplayName("미리 정의해서 쓰는게 나은 듯 하구만?")
  @RepeatedTest(value = 3, name = NAME_FOR_REPEATED_TEST)
  void repeatWithCustomConstant() {
    System.out.println("RepeatTest.repeatWithCustomConstant");
  }

  @DisplayName("변수 이용하기")
  @ParameterizedTest
  @ValueSource(strings = {"panda", "bear"})
  void parameterizedTest(String var) {
    System.out.println("var = " + var);
  }
}


/*
@RepeatedTest
오마이갓, RepeatedTest 상수 써서 이름 주면 너무 지저분해진당

@ParameterizedTest
auto-closeable 설정도 되어있어서 파일 같은 거 가져다 써도 안전~!
 */