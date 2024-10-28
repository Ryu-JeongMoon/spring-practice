package com.springanything.config.p6spy;

import jakarta.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.p6spy.engine.spy.P6SpyOptions;

@Configuration
@ConditionalOnProperty(value = "conditional.enabled.jta", havingValue = "false", matchIfMissing = true)
public class P6spyConfig {

  @PostConstruct
  public void setLogMessageFormat() {
    P6SpyOptions
      .getActiveInstance()
      .setLogMessageFormat(P6spyPrettySqlFormatter.class.getName());
  }
}
