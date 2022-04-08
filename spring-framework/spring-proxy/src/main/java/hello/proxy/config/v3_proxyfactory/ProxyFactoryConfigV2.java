package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v1.OrderControllerImplV1;
import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderRepositoryImplV1;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderServiceImplV1;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyFactoryConfigV2 {

  @Bean
  public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
    OrderRepositoryV2 orderRepository = new OrderRepositoryV2();
    return getProxy(logTrace, orderRepository);
  }

  @Bean
  public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
    OrderServiceV2 orderService = new OrderServiceV2(orderRepositoryV2(logTrace));
    return getProxy(logTrace, orderService);
  }

  @Bean
  public OrderControllerV2 orderControllerV1(LogTrace logTrace) {
    OrderControllerV2 orderController = new OrderControllerV2(orderServiceV2(logTrace));
    return getProxy(logTrace, orderController);
  }

  private <T> T getProxy(LogTrace logTrace, T t) {
    ProxyFactory factory = new ProxyFactory(t);
    factory.addAdvisor(getAdvisor(logTrace));
    return (T) factory.getProxy();
  }

  private Advisor getAdvisor(LogTrace logTrace) {
    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("request*", "order*", "save*");
    return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
  }
}
