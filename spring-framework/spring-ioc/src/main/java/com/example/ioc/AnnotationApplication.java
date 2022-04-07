package com.example.ioc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationApplication {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
    String[] beanDefinitionNames2 = annotationConfigApplicationContext.getBeanDefinitionNames();

    for (String beanDefinitionName : beanDefinitionNames2)
      System.out.println("beanDefinitionName = " + beanDefinitionName);
  }
}
