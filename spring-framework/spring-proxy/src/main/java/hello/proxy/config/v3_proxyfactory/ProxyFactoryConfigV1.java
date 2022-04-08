package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v1.OrderControllerImplV1;
import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderRepositoryImplV1;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderServiceImplV1;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyFactoryConfigV1 {

  @Bean
  public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
    OrderRepositoryV1 orderRepository = new OrderRepositoryImplV1();
    return getProxy(logTrace, orderRepository);
  }

  @Bean
  public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
    OrderServiceV1 orderService = new OrderServiceImplV1(orderRepositoryV1(logTrace));
    return getProxy(logTrace, orderService);
  }

  @Bean
  public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
    OrderControllerV1 orderController = new OrderControllerImplV1(orderServiceV1(logTrace));
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
