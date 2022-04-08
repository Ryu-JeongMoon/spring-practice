package hello.proxy.proxyfactory;

import static org.assertj.core.api.Assertions.assertThat;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {

  @Test
  @DisplayName("Interface 존재하면 JDK Proxy")
  void proxyWithInterface() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.addAdvice(new TimeAdvice());
    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    log.info("targetClass = {}", target.getClass());
    log.info("proxyClass = {}", proxy.getClass());

    proxy.save();

    assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
    assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
  }

  @Test
  @DisplayName("구체 클래스 존재하면 CGLIB Proxy")
  void proxyWithConcrete() {
    ConcreteService target = new ConcreteService();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.addAdvice(new TimeAdvice());
    ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

    log.info("targetClass = {}", target.getClass());
    log.info("proxyClass = {}", proxy.getClass());

    proxy.call();

    assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
    assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
  }

  @Test
  @DisplayName("Interface 존재해도 CGLIB Proxy")
  void proxyWithTargetClass() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(true);
    proxyFactory.addAdvice(new TimeAdvice());

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    log.info("targetClass = {}", target.getClass());
    log.info("proxyClass = {}", proxy.getClass());

    proxy.save();

    assertThat(AopUtils.isAopProxy(proxy)).isTrue();
    assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
    assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
  }
}

/*
ProxyFactory 를 사용해 프록시 생성 시 AopUtils 사용 가능
요거보다 중요한 것은 프록시 생성 책임을 스프링이 제공하는 ProxyFactory 에 넘겨버리고
즉 JDK Dynamic 쓸 건지 CGLIB 쓸 건지 신경 쓰지 않고 핵심 부가 기능에만 신경쓰면 된다
추상화해 둔 형태의 Advice 를 이용해 어떤 프록시인지 신경 쓰지 않아도 된다!?

proxyFactory.setProxyTargetClass(true); 를 통해 인터페이스가 있더라도 CGLIB 사용하도록 강제
스프링부트는 기본적으로 이 설정을 활성화해 CGLIB 사용해 프록시 만들도록 한다
 */