package com.example.ioc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XMLApplication {
  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.txt");
    String[] beanDefinitionNames1 = context.getBeanDefinitionNames();

    for (String beanDefinitionName : beanDefinitionNames1)
      System.out.println("beanDefinitionName = " + beanDefinitionName);
  }
}
