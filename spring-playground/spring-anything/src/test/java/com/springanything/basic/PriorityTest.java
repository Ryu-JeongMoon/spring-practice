package com.springanything.basic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class PriorityTest {

  private static final int LIMIT = 5;
  private static final int[] INTS = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

  @DisplayName("전위 연산자는 대소 비교 전에 값이 증가")
  @Test
  void prefixIncrementOperator() {
    // given
    int n = 0;

    // when
    for (int i : INTS) {
      log.info("value = {}", i);

      if (++n >= LIMIT) {
        break;
      }
    }

    // then
    assertThat(n).isEqualTo(5);
  }

  @DisplayName("후위 연산자는 대소 비교 후에 값이 증가")
  @Test
  void postfixIncrementOperator() {
    // given
    int n = 0;

    // when
    for (int i : INTS) {
      log.info("value = {}", i);

      if (n++ >= LIMIT) {
        break;
      }
    }

    // then
    assertThat(n).isEqualTo(6);
  }
}
