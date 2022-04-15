package org.springaop.exam;

import lombok.RequiredArgsConstructor;
import org.springaop.exam.annotation.Trace;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

  private final ExamRepository examRepository;

  @Trace
  public void request(String itemId) {
    examRepository.save(itemId);
  }

}
