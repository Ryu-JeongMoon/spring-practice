package hello.advanced.strategy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateLogTemplate {

  public void execute(Callback callback) {
    long startTime = System.currentTimeMillis();

    callback.call();

    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("resultTime={}", resultTime);
  }
}
