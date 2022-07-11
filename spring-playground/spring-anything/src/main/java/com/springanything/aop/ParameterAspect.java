package com.springanything.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ParameterAspect {

	@Before("@within(WithParameter)")
	public void addParameter(JoinPoint joinPoint) {
		Arrays.stream(joinPoint.getArgs())
			.filter(arg -> arg instanceof AopRequest)
			.forEach(arg -> ((AopRequest)arg).setName("panda"));
	}
}
