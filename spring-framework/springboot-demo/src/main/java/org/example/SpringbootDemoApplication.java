package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootDemoApplication.class, args);
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
 */