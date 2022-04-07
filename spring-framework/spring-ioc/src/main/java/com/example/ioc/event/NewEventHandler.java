package com.example.ioc.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NewEventHandler {

  @Async
  @EventListener
  public void handle(NewEvent event) {
    System.out.println("Thread => " + Thread.currentThread());
    System.out.println("Event Received, Data => " + event.getData());
  }
}

/*
event 처리할 메서드 위에 @EventListener 붙여주면 되고 메서드 이름은 자유롭게 작성 가능
동일 이벤트를 처리하는 핸들러가 여러개라면 어떻게 될까?
어떤 핸들러가 먼저 수행될지는 알 수 없으나 기본적으로 동일 스레드에서 순차 실행된다

순서를 보장하는 법
동기적 실행이어야 하고 @Order 로 순서를 명시적으로 할당해준다
비동기도 할 수는 있겠지만 귀찮아짐
먼저 수행되어야 할 것에 대한 콜백 설정해주믄 순서 설정 가능할듯

@Async 로 비동기 수행 가능
설정 클래스에서 @EnableAsync 필요
 */