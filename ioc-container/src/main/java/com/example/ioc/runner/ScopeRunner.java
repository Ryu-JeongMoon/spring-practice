package com.example.ioc.runner;

import com.example.ioc.scope.Proto;
import com.example.ioc.scope.Single;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class ScopeRunner implements ApplicationRunner {

  private final ApplicationContext context;
  private final Single single;
  private final Proto proto;

  @Override
  public void run(ApplicationArguments args) {
    System.out.println("=================================================================");
    System.out.println("ScopeRunner.run");

    System.out.println("proto");
    System.out.println(context.getBean(Proto.class));
    System.out.println(context.getBean(Proto.class));
    System.out.println(context.getBean(Proto.class));

    System.out.println("singleton");
    System.out.println(context.getBean(Single.class));
    System.out.println(context.getBean(Single.class));
    System.out.println(context.getBean(Single.class));

    System.out.println(Arrays.toString(context.getEnvironment().getActiveProfiles()));
    System.out.println(Arrays.toString(context.getEnvironment().getDefaultProfiles()));

    System.out.println("=================================================================\n");
  }
}

/*
스프링이 관리하는 빈의 일반적인 Scope 가 Singleton 이기 때문에 아래 두 놈은 같은 놈
System.out.println("proto = " + proto);
System.out.println("single.getProto() = " + single.getProto();

스프링이 관리하지만 요청마다 새로운 객체를 생성해야 하는 경우
명시적으로 스코프를 변경해주어야 한다
@Scope(scopeName = "prototype") 으로 type-safe 하지 못 하게 문자열로 지정해주어야 함

Scope 이 다르면서 의존 관계가 있는 경우 문제 발생 여지가 있다
Singleton 에서 Prototype 을 직접 가지고 있는 경우
요청 마다 달라져야 하는 Prototype 이 Singleton 에 의해 참조 되기 때문에 바뀌지 않는다
@Scope 옵션에서 proxyMode = ScopedProxyMode.TARGET_CLASS 설정하여
CGLib 기반의 Class 상속 Proxy 를 만들게 하고 Proto 에 대한 직접 참조 대신 Proto 의 Proxy 참조를 가지게 한다
Singleton -> Proxy of Proto -> Proto 로 흐름이 흘러가서 요청 마다 Proto 가 달라지게 된다?!
(JDK Proxy 는 Interface 구현하여 프록시를 만들기 때문)
 */