package hello.advanced.strategy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2 {

  public void execute(Strategy strategy) {
    long startTime = System.currentTimeMillis();

    // 비지니스 로직 시작
    strategy.call();
    // 비지니스 로직 종료

    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("resultTime = {}", resultTime);
  }
}
