package com.springanything.mail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
@ConditionalOnProperty(value = "condition.enabled.mail", havingValue = "true")
public class MailConfig {

  @Bean
  public MailHelper mailHelper(
    JavaMailSender javaMailSender,
    CustomMailProperties customMailProperties,
    SpringTemplateEngine springTemplateEngine
  ) {
    return new MailHelper(javaMailSender, customMailProperties, springTemplateEngine);
  }
}
