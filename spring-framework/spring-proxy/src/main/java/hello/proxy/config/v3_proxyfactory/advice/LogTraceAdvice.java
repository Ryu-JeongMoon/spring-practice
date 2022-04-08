package hello.proxy.config.v3_proxyfactory.advice;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
@RequiredArgsConstructor
public class LogTraceAdvice implements MethodInterceptor {

  private final LogTrace logTrace;

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    TraceStatus traceStatus = null;
    try {
      Method method = invocation.getMethod();
      String message = method.getDeclaringClass().getName() + "." + method.getName() + " called";
      traceStatus = logTrace.begin(message);
      Object result = invocation.proceed();
      logTrace.end(traceStatus);
      return result;
    } catch (Exception e) {
      logTrace.exception(traceStatus, e);
      throw e;
    }
  }
}
