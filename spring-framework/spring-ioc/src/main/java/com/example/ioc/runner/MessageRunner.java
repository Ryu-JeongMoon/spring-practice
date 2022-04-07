package com.example.ioc.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageRunner implements ApplicationRunner {

  private final MessageSource messageSource;

  @Override
  public void run(ApplicationArguments args) {
    System.out.println("=================================================================");
    System.out.println("MessageRunner.run");

    System.out.println(messageSource.getMessage("greeting", new String[]{"panda"}, Locale.KOREA));
    System.out.println(messageSource.getMessage("greeting", new String[]{"bear"}, Locale.getDefault()));

    System.out.println("=================================================================\n");
  }
}
