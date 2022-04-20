package org.example.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

  @Override
  public void onApplicationEvent(ApplicationStartedEvent event) {
    System.out.println("======================");
    System.out.println("Application Started !!");
    System.out.println("======================");
  }
}

/*
ApplicationStartedEvent 발생 시점에는 @Component 등록된 상태?!
 */