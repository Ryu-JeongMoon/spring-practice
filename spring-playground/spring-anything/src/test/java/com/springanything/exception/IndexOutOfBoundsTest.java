package com.springanything.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IndexOutOfBoundsTest {

  int[] array1;
  int[] array2;

  @BeforeEach
  void setUp() {
    array1 = new int[50];
    array2 = new int[50];
  }

  @DisplayName("예외 터트려 버리기~~")
  @RepeatedTest(10)
  void index() {
    // given
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    // when
    int i = 0;
    try {
      while (true)
        log.info("i = {}, value = {}", i, array1[i++]);
    } catch (ArrayIndexOutOfBoundsException e) {
      log.info("i = {}", i);
    }

    // then
    stopWatch.stop();
    log.info("time: {}", stopWatch.prettyPrint());
  }

  @DisplayName("for-each loop")
  @RepeatedTest(10)
  void forLoop() {
    // given
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    // when
    for (int i : array2) {
      log.info("value = {}", i);
    }

    // then
    stopWatch.stop();
    log.info("time: {}", stopWatch.prettyPrint());
  }
}
