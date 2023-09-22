package com.springanything.mutlithread;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class PatternTimeComparisonTest {

  @RepeatedTest(10)
  @DisplayName("미리 컴파일 후 synchronized 블록")
  void precompiled() {
    // given
    StopWatch stopWatch = new StopWatch();
    stopWatch.start("pre-compiled");

    final String userAgent = "Linux Hoy!";

    // when
    for (int i = 0; i < 10; i++) {
      log.info("operatingSystems = {}", OperatingSystems.fromWithSynchronization(userAgent));
    }
    stopWatch.stop();

    // then
    log.info("execution time = {}", stopWatch.prettyPrint());
  }

  @RepeatedTest(10)
  @DisplayName("비교할 때마다 Pattern 객체 생성")
  void construct() {
    // given
    StopWatch stopWatch = new StopWatch();
    stopWatch.start("construct");

    final String userAgent = "Linux Hoy!";

    // when
    for (int i = 0; i < 10; i++) {
      log.info("operatingSystems = {}", OperatingSystems.fromWithConstructor(userAgent));
    }
    stopWatch.stop();

    // then
    log.info("execution time = {}", stopWatch.prettyPrint());
  }
}
