package hello.proxy.app.v2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderRepositoryV2 {

  public void save(String itemId) {
    if ("ex".equals(itemId))
      throw new IllegalStateException();

    sleep(1000);
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      log.info("error = {}", e);
    }
  }
}
