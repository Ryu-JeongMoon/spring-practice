package org.example.listener;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

public class StartingListener implements ApplicationListener<ApplicationStartingEvent> {

  @Override
  public void onApplicationEvent(ApplicationStartingEvent event) {
    System.out.println("====================");
    System.out.println("Application Start !!");
    System.out.println("====================");
  }
}

/*
ApplicationStartingEvent 발생 시점에는 @Component 가 생성되어있지 않은 상태
SpringbootDemoApplication 에 직접 등록해놔야 함
 */