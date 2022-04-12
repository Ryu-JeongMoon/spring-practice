package org.springaop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

  @Order(2)
  @Aspect
  public static class LogAspect {

    @Around("org.springaop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
      log.info("[log] signature = {}", joinPoint.getSignature());
      return joinPoint.proceed();
    }
  }

  @Order(1)
  @Aspect
  public static class TransactionAspect {

    @Around("org.springaop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
      try {
        log.info("[transaction start] {}", joinPoint.getSignature());
        Object result = joinPoint.proceed();
        log.info("[transaction commit] {}", joinPoint.getSignature());
        return result;
      } catch (Exception e) {
        log.info("[transaction rollback] {}", joinPoint.getSignature());
        throw e;
      } finally {
        log.info("[resource release] {}", joinPoint.getSignature());
      }
    }
  }
}

/*
순서는 @Aspect 에서 제어할 수 있다
순서 정의가 필요한 경우에는 관리 편의성을 위해 static nested 로 만드는 수 밖에 ?!
 */