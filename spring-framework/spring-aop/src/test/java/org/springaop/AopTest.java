package org.springaop;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springaop.order.OrderRepository;
import org.springaop.order.OrderService;
import org.springaop.order.aop.AspectV1;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(AspectV1.class)
public class AopTest {

  @Autowired
  OrderRepository orderRepository;
  @Autowired
  OrderService orderService;

  @Test
  @DisplayName("AOP 정보 확인")
  void aopInfo() {
    log.info("AopUtils.isAopProxy(orderRepository) = {}", AopUtils.isAopProxy(orderRepository));
    log.info("AopUtils.isAopProxy(orderService) = {}", AopUtils.isAopProxy(orderService));
  }

  @Test
  @DisplayName("")
  void success() {
    orderService.orderItem("itemA");
  }

  @Test
  @DisplayName("예외 발생")
  void exception() {
    assertThatIllegalStateException()
      .isThrownBy(() -> orderService.orderItem("ex"));
  }
}
