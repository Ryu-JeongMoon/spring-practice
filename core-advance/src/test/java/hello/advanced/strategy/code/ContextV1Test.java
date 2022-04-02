package hello.advanced.strategy.code;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContextV1Test {

  @Test
  @DisplayName("")
  void strategyV1() {
    Strategy strategyLogic1 = new StrategyLogic1();
    ContextV1 contextV1 = new ContextV1(strategyLogic1);
    contextV1.execute();

    Strategy strategyLogic2 = new StrategyLogic2();
    ContextV1 contextV2 = new ContextV1(strategyLogic2);
    contextV2.execute();
  }
}