package hello.proxy.config.v1_proxy.concretebased;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderControllerConcreteProxy extends OrderControllerV2 {

  private final OrderControllerV2 target;
  private final LogTrace logTrace;

  public OrderControllerConcreteProxy(OrderControllerV2 target, LogTrace logTrace) {
    super(null);
    this.target = target;
    this.logTrace = logTrace;
  }

  @Override
  public String request(String itemId) {
    TraceStatus traceStatus = null;
    try {
      traceStatus = logTrace.begin("OrderControllerConcreteProxy Called");
      String request = target.request(itemId);
      logTrace.end(traceStatus);
      return request;
    } catch (Exception e) {
      logTrace.exception(traceStatus, e);
      throw e;
    }
  }

  @Override
  public String noLog() {
    return target.noLog();
  }
}
