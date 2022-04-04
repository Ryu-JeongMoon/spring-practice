package hello.proxy.concrete.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteLogicProxy extends ConcreteLogic {

  @Override
  public String operate() {
    log.info("ConcreteLogicProxy Called");

    long startTime = System.currentTimeMillis();
    String result = super.operate();
    long endTime = System.currentTimeMillis();

    log.info("result = {}", result);
    log.info("execution time = {}", (endTime - startTime));
    return result;
  }
}
