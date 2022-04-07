package com.example.ioc;

import com.example.outer.OuterBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class IocContainerApplication {

  // addInitializers 활용한 functional 등록 방법
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(IocContainerApplication.class);
    springApplication
        .addInitializers(
            (ApplicationContextInitializer<GenericApplicationContext>) ctx -> ctx.registerBean(OuterBean.class)
        );

    springApplication.run();
  }

  // messages.properties 수정 후 rebuild 해주면 reloadable 이 다시 읽어옴
  // 즉 운영 중에 메세지 수정도 가능한 것
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:/messages");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setCacheSeconds(3);
    return messageSource;
  }
}

/*
default 버전
public static void main(String[] args) {
  SpringApplication.run(IocContainerApplication.class, args);
}

default 버전보다 functional 한 방식이 성능 상 약간 유리한 점은 있으나
관리해야할 모든 빈을 저런 식으로 수동 등록한다?! 그냥 미친놈임
애초에 ComponentScan 은 구동 시 초기에만 조지고 그 이후에 비용 들어갈 일이 없으므로
성능 상의 문제라는 건 초기 구동 시간이 약간 더 걸린다는 것
서버는 빨리 켜지는 것이 중요한 가치가 아니므로 간편한 방식을 사용하자
 */