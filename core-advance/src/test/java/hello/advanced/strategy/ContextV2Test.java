package hello.advanced.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV2;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class ContextV2Test {

  @Test
  @DisplayName("인자로 넘기기")
  void strategyV1() {
    ContextV2 context = new ContextV2();
    context.execute(new StrategyLogic1());
    context.execute(new StrategyLogic2());
  }

  @Test
  @DisplayName("익명 내부 클래스")
  void strategyV2() {
    ContextV2 context = new ContextV2();
    context.execute(new Strategy() {
      @Override
      public void call() {
        log.info("비지니스 로직1 실행");
      }
    });
    context.execute(new Strategy() {
      @Override
      public void call() {
        log.info("비지니스 로직2 실행");
      }
    });
  }

  @Test
  @DisplayName("람다")
  void strategyV3() {
    ContextV2 context = new ContextV2();
    context.execute(() -> log.info("비지니스 로직1 실행"));
    context.execute(() -> log.info("비지니스 로직2 실행"));
  }
}