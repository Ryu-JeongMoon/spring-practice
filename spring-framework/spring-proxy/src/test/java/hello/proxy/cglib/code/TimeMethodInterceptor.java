package hello.proxy.cglib.code;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

@Slf4j
@RequiredArgsConstructor
public class TimeMethodInterceptor implements MethodInterceptor {

  private final Object target;

  @Override
  public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
    log.info("TimeMethodInterceptor start");
    long startTime = System.currentTimeMillis();
    Object result = methodProxy.invoke(target, args);
    long endTime = System.currentTimeMillis();

    log.info("result = {}", result);
    log.info("execution time = {}", (endTime - startTime));
    log.info("TimeMethodInterceptor end");

    return result;
  }
}
