package hello.proxy.postprocessor;

import static org.junit.jupiter.api.Assertions.assertThrows;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

public class BeanPostProcessorTest {

  @Test
  @DisplayName("A -> B 갈아 끼워버리기")
  void postprocess() {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);
    B beanA = applicationContext.getBean("beanA", B.class);
    beanA.hello();

    assertThrows(
      NoSuchBeanDefinitionException.class,
      () -> applicationContext.getBean(A.class)
    );
  }

  @Configuration
  static class BasicConfig {

    @Bean(name = "beanA")
    public A a() {
      return new A();
    }

    @Bean
    public AtoBPostProcessor atoBPostProcessor() {
      return new AtoBPostProcessor();
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

  @Slf4j
  static class AtoBPostProcessor implements BeanPostProcessor {

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
      log.info("bean = {}, beanName = {}", bean, beanName);
      return bean instanceof A ? new B() : bean;
    }
  }
}
