package com.example.ioc.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OldEvent extends ApplicationEvent {

  private int data;

  public OldEvent(Object source) {
    super(source);
  }

  public OldEvent(Object source, int data) {
    super(source);
    this.data = data;
  }
}

/*
spring 4.2 이전 버전에서는 반드시 extends ApplicationEvent 로 상속 받아야 했다
이는 스프링이 추구하는 비침투성 (transparent) 과 상반 되기에 4.2 이후 버전에서는 POJO 로 만드는게 가능해졌다
 */