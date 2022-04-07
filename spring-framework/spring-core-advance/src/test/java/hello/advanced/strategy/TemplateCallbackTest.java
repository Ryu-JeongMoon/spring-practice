package hello.advanced.strategy;

import hello.advanced.trace.strategy.code.template.TimeLogTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateCallbackTest {

  @Test
  @DisplayName("")
  void callbackV1() {
    TimeLogTemplate template = new TimeLogTemplate();
    template.execute(() -> log.info("비지니스 로직1 실행"));
    template.execute(() -> log.info("비지니스 로직2 실행"));
  }
}
