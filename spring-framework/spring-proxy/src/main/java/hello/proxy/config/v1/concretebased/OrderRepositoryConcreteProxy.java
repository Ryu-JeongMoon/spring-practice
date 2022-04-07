package hello.proxy.config.v1.concretebased;

import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryConcreteProxy extends OrderRepositoryV2 {

  private final OrderRepositoryV2 target;
  private final LogTrace logTrace;

  @Override
  public void save(String itemId) {
    TraceStatus traceStatus = null;
    try {
      traceStatus = logTrace.begin("OrderRepositoryConcreteProxy Called");
      target.save(itemId);
      logTrace.end(traceStatus);
    } catch (Exception e) {
      logTrace.exception(traceStatus, e);
      throw e;
    }
  }
}
