package com.springservletthymeleaf.basic.itemservice.config.p6spy;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.p6spy.engine.spy.P6SpyOptions;

@Configuration
@Profile(value = "local")
public class P6spyConfig {

  @PostConstruct
  public void setLogMessageFormat() {
    P6SpyOptions
        .getActiveInstance()
        .setLogMessageFormat(P6spyPrettySqlFormatter.class.getName());
  }
}
