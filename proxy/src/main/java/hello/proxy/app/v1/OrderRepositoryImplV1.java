package hello.proxy.app.v1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderRepositoryImplV1 implements OrderRepositoryV1 {

  @Override
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
