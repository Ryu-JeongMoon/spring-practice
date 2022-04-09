package hello.proxy.postprocessor;

import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BasicTest {

  @Test
  @DisplayName("Spring Container 생성")
  void postprocess() {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);
    A beanA = applicationContext.getBean("beanA", A.class);
    beanA.hello();

    assertThrows(
      NoSuchBeanDefinitionException.class,
      () -> applicationContext.getBean("b", B.class)
    );
  }

  @Configuration
  static class BasicConfig {

    @Bean(name = "beanA")
    public A a() {
      return new A();
    }
  }

  @Slf4j
  static class A {

    public void hello() {
      log.info("hello A");
    }
  }

  @Slf4j
  static class B {

    public void hello() {
      log.info("hello B");
    }
  }
}
