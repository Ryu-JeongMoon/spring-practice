package org.springaop.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springaop.exam.annotation.Retry;

@Slf4j
@Aspect
public class RetryAspect {

  @Around("@annotation(retry)")
  public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
    log.info("[retry] {} annotation = {}", joinPoint.getSignature(), retry);

    int maxRetryCount = retry.value();
    Exception exceptionHolder = new IllegalStateException();

    for (int retryCount = 1; retryCount <= maxRetryCount; retryCount++) {
      try {
        log.info("[retry] try-count = {}/{}", retryCount, maxRetryCount);
        return joinPoint.proceed();
      } catch (Exception e) {
        exceptionHolder = e;
      }
    }
    throw exceptionHolder;
  }
}

/*
Throwable 은 개발자가 처리할 수 없는 문제이므로 try-catch 처리 없이 냅다 집어던진다
OutOfMemory 등등의 문제
 */