package org.springframeworkcore;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.CacheControl;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframeworkcore.formatter.Person;
import org.springframeworkcore.interceptor.AnotherInterceptor;
import org.springframeworkcore.interceptor.GreetingInterceptor;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {

  }

//  @Override
//  public void addFormatters(FormatterRegistry registry) {
//    WebMvcConfigurer.super.addFormatters(registry);
//    registry.addFormatter(new PersonFormatter());
//  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new GreetingInterceptor()).order(0);
    registry.addInterceptor(new AnotherInterceptor()).order(Ordered.HIGHEST_PRECEDENCE);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/mobile/**")
        .addResourceLocations("classpath:mobile/")
        .setCacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS));
  }

  @Bean
  public Jaxb2Marshaller jaxb2Marshaller() {
    Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
    jaxb2Marshaller.setPackagesToScan(Person.class.getPackageName());
    return jaxb2Marshaller;
  }
}

/*
스프링부트를 사용한다면 자동 설정이 웬만한 건 되지만 확장 포인트가 필요한 경우에
WebMvcConfigurer 인터페이스를 구현한 설정 클래스에서 오버라이드를 하여 사용할 수 있다
직접 ViewResolver 만들어 등록할 필요 없다

addFormatters()에서 Mapping 방식을 설정해놓은
Custom-Formatter 등록할 수 있으나 @Component 등록해도 오께이

Interceptor vs Filter ?
WebMVC 특화 기능인 경우에 Interceptor 사용하고 나머지 로깅이나 보안에는 Filter 사용할 것
addInterceptors()로 Interceptor 추가 가능하고
명시적인 순서 주지 않으면 등록된 순서대로 실행됨
 */