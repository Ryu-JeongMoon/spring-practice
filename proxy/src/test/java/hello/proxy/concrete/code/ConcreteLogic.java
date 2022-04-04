package hello.proxy.concrete.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConcreteLogic {

  public String operate() {
    log.info("ConcreteLogic Called");
    return "data";
  }

}
