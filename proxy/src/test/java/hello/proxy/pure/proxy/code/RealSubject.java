package hello.proxy.pure.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RealSubject implements Subject {

  @Override
  public String operate() {
    log.info("RealSubject Called");
    sleep(1000);
    return "data";
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      log.info("error = {}", e);
    }
  }
}
