package com.springanything.aop;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {

  @Around("@within(requestResponseLogging)")
  public Object logAround(final ProceedingJoinPoint joinPoint, RequestResponseLogging requestResponseLogging) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = signature.getMethod().getName();

    if (requestResponseLogging.requestEnabled()) {
      String[] parameterNames = signature.getParameterNames();
      Object[] args = joinPoint.getArgs();

      String request = IntStream.range(0, parameterNames.length)
        .filter(i -> !(args[i] instanceof HttpServletRequest))
        .mapToObj(i -> parameterNames[i] + "=" + args[i])
        .collect(Collectors.joining(", "));

      log.info("[{}][{}] request : {}", className, methodName, request);
    }

    Object response = joinPoint.proceed();
    if (requestResponseLogging.responseEnabled()) {
      log.info("[{}][{}] response : {}", className, methodName, response);
    }

    return response;
  }
}
