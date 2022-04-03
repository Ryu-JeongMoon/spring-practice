package hello.proxy.config;

import hello.proxy.app.v1.OrderControllerImplV1;
import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.app.v1.OrderRepositoryImplV1;
import hello.proxy.app.v1.OrderRepositoryV1;
import hello.proxy.app.v1.OrderServiceImplV1;
import hello.proxy.app.v1.OrderServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigV1 {

  @Bean
  public OrderRepositoryV1 orderRepositoryV1() {
    return new OrderRepositoryImplV1();
  }

  @Bean
  public OrderServiceV1 orderServiceV1() {
    return new OrderServiceImplV1(orderRepositoryV1());
  }

  @Bean
  public OrderControllerV1 orderControllerV1() {
    return new OrderControllerImplV1(orderServiceV1());
  }

}
