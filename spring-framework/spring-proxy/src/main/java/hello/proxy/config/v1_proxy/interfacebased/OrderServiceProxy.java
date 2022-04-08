package hello.proxy.config.v1_proxy.interfacebased;

import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceProxy implements OrderServiceV1 {

  private final OrderServiceV1 target;
  private final LogTrace logTrace;

  @Override
  public void orderItem(String itemId) {
    TraceStatus traceStatus = null;
    try {
      traceStatus = logTrace.begin("OrderServiceProxy Called");
      target.orderItem(itemId);
      logTrace.end(traceStatus);
    } catch (Exception e) {
      logTrace.exception(traceStatus, e);
      throw e;
    }
  }
}
