package hello.proxy.config.v1;

import hello.proxy.app.v1.OrderControllerImplV1;
import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderRepositoryImplV1;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderServiceImplV1;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.config.v1.interfacebased.OrderControllerProxy;
import hello.proxy.config.v1.interfacebased.OrderRepositoryProxy;
import hello.proxy.config.v1.interfacebased.OrderServiceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyConfig {

  @Bean
  public OrderControllerV1 orderController(LogTrace logTrace) {
    return new OrderControllerProxy(new OrderControllerImplV1(orderService(logTrace)), logTrace);
  }

  @Bean
  public OrderServiceV1 orderService(LogTrace logTrace) {
    return new OrderServiceProxy(new OrderServiceImplV1(orderRepository(logTrace)), logTrace);
  }

  private OrderRepositoryV1 orderRepository(LogTrace logTrace) {
    return new OrderRepositoryProxy(new OrderRepositoryImplV1(), logTrace);
  }

}
