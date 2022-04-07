package com.example.ioc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.ioc")
public class ApplicationConfig {

}

/*
basePackages 옵션은 문자열을 사용하기 때문에 type-safe 하지 않다
basePackageClasses 얘를 사용하면 클래스 타입으로 줄 수 있다

@ComponentScan 의 핵심 두가지는
1. 어디서부터 스캔을 할 것인지 범위 설정
2. 어떤 것들을 빼고 스캔할 것인지 excludeFilters 설정
 */