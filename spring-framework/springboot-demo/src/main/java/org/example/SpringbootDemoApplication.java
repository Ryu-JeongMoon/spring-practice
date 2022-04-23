package org.example;

import org.example.listener.StartingListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootDemoApplication {

  public static void main(String[] args) {
    // listener registration -> StartingListener
    SpringApplication springApplication = new SpringApplication(SpringbootDemoApplication.class);
    springApplication.addListeners(new StartingListener());
    springApplication.setWebApplicationType(WebApplicationType.SERVLET);
    springApplication.run(args);
  }

  @Bean
  @ConditionalOnMissingBean
  public Panda preliminaryPanda() {
    return new Panda("bear", 5999);
  }
}

/*
keytool -genkey
  -alias tomcat
  -storetype PKCS12
  -keyalg RSA
  -keysize 2048
  -keystore keystore.p12
  -validity 4000

banner -> resources/banner.txt | jpg | png 가능

- static run
SpringApplication.run(SpringbootDemoApplication.class, args);

- builder
new SpringApplicationBuilder()
  .sources(SpringbootDemoApplication.class)
  .run(args);

springApplication.setWebApplicationType(WebApplicationType.REACTIVE);
REACTIVE 로 설정 시 h2-console 접속 불가
반드시 SERVLET 으로 돌릴 것
 */