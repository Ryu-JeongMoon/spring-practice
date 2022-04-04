package hello.proxy.concrete.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ConcreteClient {

  private final ConcreteLogic concreteLogic;

  public void execute() {
    String result = concreteLogic.operate();
    log.info("result = {}", result);
  }
}
