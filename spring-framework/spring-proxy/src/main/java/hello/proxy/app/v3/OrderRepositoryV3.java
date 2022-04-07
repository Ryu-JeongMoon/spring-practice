package hello.proxy.app.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OrderRepositoryV3 {

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
