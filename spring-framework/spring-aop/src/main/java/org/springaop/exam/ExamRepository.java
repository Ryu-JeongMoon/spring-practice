package org.springaop.exam;

import org.springaop.exam.annotation.Retry;
import org.springaop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {

  private static int sequence = 0;

  @Trace
  @Retry
  public String save(String itemId) {
    if (++sequence % 5 == 0)
      throw new IllegalStateException("5번에 1번 실패해버리기");

    return "ok";
  }
}
