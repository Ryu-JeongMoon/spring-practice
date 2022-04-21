package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PandaConfig {

  @Bean
  public Panda panda() {
    return new Panda("bear", 99);
  }
}
