package hello.proxy.advisor;

import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

@Slf4j
public class MultiAdvisorTest {

  @Test
  @DisplayName("직접 여러 프록시를 생성")
  void multiAdvisor1() {
    // client -> proxy2 -> proxy1 -> target
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory1 = new ProxyFactory(target);
    proxyFactory1.addAdvice(new Advice1());
    ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

    ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
    proxyFactory2.addAdvice(new Advice2());
    ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();

    proxy2.save();
  }

  @Test
  @DisplayName("하나의 프록시에 여러 어드바이저 적용")
  void multiAdvisor2() {
    // 등록된 순서대로 호출됨
    // client -> proxy -> advisor1 -> advisor2 -> target
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(new Advice1());
    DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(new Advice2());
    proxyFactory.addAdvisors(List.of(advisor1, advisor2));

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
  }

  static class Advice1 implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
      log.info("advisor1 호출");
      return invocation.proceed();
    }
  }

  static class Advice2 implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
      log.info("advisor2 호출");
      return invocation.proceed();
    }
  }
}

/*
Spring AOP 는 최적화를 위해 Advisor 마다 Proxy 를 따로 생성하는 것이 아니다
프록시는 하나만 생성해두고 여러 어드바이저를 걸쳐놓는다
등록된 순서대로 돌고 마지막에 target 호출
 */