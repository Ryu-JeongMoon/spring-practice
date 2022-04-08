package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    log.info("TimeAdvice Called");

    long startTime = System.currentTimeMillis();
    Object result = invocation.proceed();
    long endTime = System.currentTimeMillis();

    log.info("result = {}", result);
    log.info("execution time = {}", (endTime - startTime));
    return result;
  }
}

/*
JDK Proxy -> InvocationHandler
CGLIB -> MethodInterceptor
Spring ProxyFactory -> MethodInterceptor (Advice)

스프링은 JDK, CGLIB 에서 사용되는 로직을 추상화한 형태로 제공
CGLIB, ProxyFactory 에서 사용되는 MethodInterceptor 이름은 같지만 다른 패키지에 존재
 */