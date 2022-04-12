package org.springaop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class AspectV6Advice {

  @Around("org.springaop.order.aop.Pointcuts.orderAndService()")
  public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      // @Before
      log.info("[transaction start] {}", joinPoint.getSignature());
      Object result = joinPoint.proceed();
      // @AfterReturning
      log.info("[transaction commit] {}", joinPoint.getSignature());
      return result;
    } catch (Exception e) {
      // @AfterThrowing
      log.info("[transaction rollback] {}", joinPoint.getSignature());
      throw e;
    } finally {
      // @After
      log.info("[resource release] {}", joinPoint.getSignature());
    }
  }

  @Before("org.springaop.order.aop.Pointcuts.orderAndService()")
  public void doBefore(JoinPoint joinPoint) {
    log.info("doBefore");
  }

  @AfterReturning(value = "org.springaop.order.aop.Pointcuts.orderAndService()", returning = "result")
  public void doAfterReturning(JoinPoint joinPoint, Object result) {
    log.info("doAfterReturning");
    log.info("result = {}", result);
  }

  @AfterThrowing(value = "org.springaop.order.aop.Pointcuts.orderAndService()", throwing = "e")
  public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
    log.info("doAfterThrowing");
    log.info("e = {}", e.getMessage());
  }

  @After("org.springaop.order.aop.Pointcuts.orderAndService()")
  public void doAfter(JoinPoint joinPoint) {
    log.info("doAfter");
  }

}

/*
@Around 에서만 ProceedingJoinPoint 를 사용해 target 호출
즉 @Around 에서는 반드시 ProceedingJoinPoint.proceed() 를 호출해야 함

그 외에 나머지는 JoinPoint 를 사용해 호출 없이 각각의 위치에서만 부가 기능 수행
 */