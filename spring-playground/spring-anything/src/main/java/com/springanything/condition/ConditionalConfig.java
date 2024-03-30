package com.springanything.condition;

import jakarta.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@ConditionalOnProperty(name = "springanything.conditional", havingValue = "TRuE")
public class ConditionalConfig {

  // This method is called even if it's access modifier is private.
  @PostConstruct
  private void init() {
    log.info("ConditionalConfig initialized");
  }
}
