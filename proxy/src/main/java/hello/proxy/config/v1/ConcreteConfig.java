package hello.proxy.config.v1;

import hello.proxy.app.v2.OrderControllerV2;
import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.config.v1.concretebased.OrderControllerConcreteProxy;
import hello.proxy.config.v1.concretebased.OrderRepositoryConcreteProxy;
import hello.proxy.config.v1.concretebased.OrderServiceConcreteProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteConfig {

  @Bean
  public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
    return new OrderControllerConcreteProxy(new OrderControllerV2(orderServiceV2(logTrace)),
      logTrace);
  }

  @Bean
  public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
    return new OrderServiceConcreteProxy(new OrderServiceV2(orderRepositoryV2(logTrace)), logTrace);
  }

  private OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
    return new OrderRepositoryConcreteProxy(new OrderRepositoryV2(), logTrace);
  }

}
