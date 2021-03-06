package hello.proxy.config.v2_dynamicproxy.dynamic;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class LogTraceBasicHandler implements InvocationHandler {

  private final Object target;
  private final LogTrace logTrace;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    TraceStatus traceStatus = null;
    try {
      String message = method.getDeclaringClass().getName() + "." + method.getName() + " called";
      traceStatus = logTrace.begin(message);
      Object result = method.invoke(target, args);
      logTrace.end(traceStatus);
      return result;
    } catch (Exception e) {
      logTrace.exception(traceStatus, e);
      throw e;
    }
  }
}
