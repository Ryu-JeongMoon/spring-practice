package hello.proxy.config.v2;

import hello.proxy.app.v1.OrderControllerImplV1;
import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderRepositoryImplV1;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderServiceImplV1;
import hello.proxy.app.v1.OrderServiceV1;
import hello.proxy.config.v2.dynamic.LogTraceBasicHandler;
import hello.proxy.trace.logtrace.LogTrace;
import java.lang.reflect.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicProxyConfig {

  @Bean
  public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
    OrderRepositoryV1 orderRepository = new OrderRepositoryImplV1();
    return (OrderRepositoryV1) Proxy.newProxyInstance(
      OrderRepositoryV1.class.getClassLoader(),
      new Class[]{ OrderRepositoryV1.class },
      new LogTraceBasicHandler(orderRepository, logTrace));
  }

  @Bean
  public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
    OrderServiceV1 orderService = new OrderServiceImplV1(orderRepositoryV1(logTrace));
    return (OrderServiceV1) Proxy.newProxyInstance(
      OrderServiceV1.class.getClassLoader(),
      new Class[]{ OrderServiceV1.class },
      new LogTraceBasicHandler(orderService, logTrace));
  }

  @Bean
  public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
    OrderControllerV1 orderRepository = new OrderControllerImplV1(orderServiceV1(logTrace));
    return (OrderControllerV1) Proxy.newProxyInstance(
      OrderControllerV1.class.getClassLoader(),
      new Class[]{ OrderControllerV1.class },
      new LogTraceBasicHandler(orderRepository, logTrace));
  }
}
