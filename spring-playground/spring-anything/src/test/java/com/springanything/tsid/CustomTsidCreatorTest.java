package com.springanything.tsid;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class CustomTsidCreatorTest {

  private static final int COUNT = 3;

  @DisplayName("random() 사용 시 인식할 수 없는 문자 생성")
  @Test
  void random() {
    // given
    String random = RandomStringUtils.random(COUNT);

    // when
    log.info("random: {}", random);

    // then
    assertThat(random.length()).isEqualTo(COUNT);
  }

  @DisplayName("무작위 확률을 늘리고 싶다면 randomAlphanumeric() 사용")
  @Test
  void randomAlphanumeric() {
    // given
    String random = RandomStringUtils.randomAlphanumeric(COUNT);

    // when
    log.info("random: {}", random);

    // then
    assertThat(random.length()).isEqualTo(COUNT);
  }
}
