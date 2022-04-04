package hello.proxy.config.v1.interfacebased;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerProxy implements OrderControllerV1 {

  private final OrderControllerV1 target;
  private final LogTrace logTrace;

  @Override
  public String request(String itemId) {
    TraceStatus traceStatus = null;
    try {
      traceStatus = logTrace.begin("OrderControllerProxy Called");
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
