package org.springaop.exam;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springaop.exam.aop.RetryAspect;
import org.springaop.exam.aop.TraceAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import({ TraceAspect.class, RetryAspect.class })
class ExamServiceTest {

  @Autowired
  ExamService examService;

  @Test
  @DisplayName("5번에 1번 실패")
  void exam() {
    for (int i = 0; i < 5; i++) {
      examService.request("data " + i);
      log.info("client request index = {}", i);
    }
  }
}