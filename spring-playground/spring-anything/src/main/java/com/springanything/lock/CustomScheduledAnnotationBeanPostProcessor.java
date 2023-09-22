package com.springanything.lock;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.util.StringValueResolver;

@Configuration
public class CustomScheduledAnnotationBeanPostProcessor extends ScheduledAnnotationBeanPostProcessor {

  @Override
  public void setEmbeddedValueResolver(StringValueResolver resolver) {
    super.setEmbeddedValueResolver(str -> str.contains("*") ? "*/10 * * * * *" : str);
  }
}
