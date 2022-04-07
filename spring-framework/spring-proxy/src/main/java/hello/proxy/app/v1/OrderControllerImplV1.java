package hello.proxy.app.v1;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerImplV1 implements OrderControllerV1 {

  private final OrderServiceV1 orderService;

  @Override
  public String request(String itemId) {
    orderService.orderItem(itemId);
    return "ok";
  }

  @Override
  public String noLog() {
    return "ok";
  }
}
