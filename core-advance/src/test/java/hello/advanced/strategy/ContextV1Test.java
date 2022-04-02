package hello.advanced.strategy;

import hello.advanced.strategy.code.ContextV1;
import hello.advanced.strategy.code.Strategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

  @Test
  @DisplayName("걍 때려박기")
  void strategyV1() {
    logic1();
    logic2();
  }

  private void logic1() {
    long startTime = System.currentTimeMillis();

    // 비지니스 로직 시작
    log.info("비지니스 로직 실행 1");
    // 비지니스 로직 종료

    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("resultTime = {}", resultTime);
  }

  private void logic2() {
    long startTime = System.currentTimeMillis();

    // 비지니스 로직 시작
    log.info("비지니스 로직 실행 2");
    // 비지니스 로직 종료

    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("resultTime = {}", resultTime);
  }

  @Test
  @DisplayName("익명 클래스를 이용하여 필요할 때마다 만들어내기")
  void strategyV2() {
    Strategy strategy1 = new Strategy() {

      @Override
      public void call() {
        log.info("비지니스 로직 실행 1");
      }
    };

    Strategy strategy2 = new Strategy() {

      @Override
      public void call() {
        log.info("비지니스 로직 실행 2");
      }
    };

    ContextV1 contextV1 = new ContextV1(strategy1);
    ContextV1 contextV2 = new ContextV1(strategy2);

    contextV1.execute();
    contextV2.execute();
  }

  @Test
  @DisplayName("인라인 익명 클래스")
  void strategyV3() {
    ContextV1 contextV1 = new ContextV1(new Strategy() {

      @Override
      public void call() {
        log.info("비지니스 로직 실행 1");
      }
    });
    ContextV1 contextV2 = new ContextV1(new Strategy() {

      @Override
      public void call() {
        log.info("비지니스 로직 실행 2");
      }
    });

    contextV1.execute();
    contextV2.execute();
  }

  /**
   * 선 조립 하는 것이 문제다, 필요할 때마다 미리 만들어두어야 한다<br/> 전략의 변경이 필요한 순간에 setter 등의 방법으로 바꾸려 하면 동시성 이슈가 발생할 수 있다<br/> 더 나은 방법은 새로운 Context 를
   * 만들고 얘를 이용하는 것이다
   */
  @Test
  @DisplayName("선 조립, 후 실행")
  void strategyV4() {
    ContextV1 contextV1 = new ContextV1(() -> log.info("비지니스 로직 실행 1"));
    ContextV1 contextV2 = new ContextV1(() -> log.info("비지니스 로직 실행 2"));

    contextV1.execute();
    contextV2.execute();
  }

}
