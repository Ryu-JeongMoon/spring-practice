package com.example.ioc.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SecondNewEventHandler {

  @Async
  @EventListener
  public void handle(NewEvent event) {
    System.out.println("Thread => " + Thread.currentThread());
    System.out.println("Event Received, Data => " + event.getData());
  }
}