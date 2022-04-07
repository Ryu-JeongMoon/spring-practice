package hello.proxy.config.v2.dynamic;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

@Slf4j
@RequiredArgsConstructor
public class LogTraceFilterHandler implements InvocationHandler {

  private final Object target;
  private final LogTrace logTrace;
  private final String[] patterns;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    String methodName = method.getName();
    if (!PatternMatchUtils.simpleMatch(patterns, methodName))
      return method.invoke(target, args);

    TraceStatus traceStatus = null;
    try {
      String message = method.getDeclaringClass().getName() + "." + methodName + "() called";
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
