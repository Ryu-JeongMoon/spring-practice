package com.springanything.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.springanything.AbstractIntegrationTest;

class ConfigBeanTest extends AbstractIntegrationTest {

  @Autowired
  private ApplicationContext context;

  @DisplayName("@Configuration 설정 + 빈 이름 지정 하지 않을 시 canonical name으로 생성")
  @Test
  void beanName() {
    // given, when
    Object bean = context.getBean("configBean");

    // then
    assertThat(bean).isInstanceOf(ConfigBean.class);

    // todo, 동작 방식 달라진 듯
    // assertThatThrownBy(() -> context.getBean("configBean"))
    // 	.isInstanceOf(NoSuchBeanDefinitionException.class);
  }
}

/*
ConfigBean
@Configuration을 통해 빈으로 등록해야 하는데 ComponentScan Filter에 의해 빈으로 등록되지 않음

DelegateConfig
@Import를 통해 ConfigBean을 등록,
DelegateConfig은 Configuration이므로 ComponentScan Filter에 의해 빈으로 등록되지 않음

MainConfig
기본 설정 클래스, @ComponentScan을 통해 @Configuration을 제외한 모든 클래스를 스캔하여 빈으로 등록

위와 같은 조건을 만족할 시
org.springframework.beans.factory.NoUniqueBeanDefinitionException:
  No qualifying bean of type 'com.springanything.mvc.AbstractBean' available:
  expected single matching bean but found 2: concreteBean,com.springanything.mvc.ConfigBean

ConfigBean이 configBean으로 등록되지 않고 canonical name으로 등록되어 발생하는 문제
 */
