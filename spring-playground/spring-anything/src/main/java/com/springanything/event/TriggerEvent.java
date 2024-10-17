package com.springanything.event;

import java.io.Serial;

import org.springframework.context.ApplicationEvent;

public class TriggerEvent extends ApplicationEvent {

  @Serial
  private static final long serialVersionUID = 7888340796322523448L;

  public TriggerEvent(Object source) {
    super(source);
  }

  @Override
  public Trigger getSource() {
    return (Trigger) super.getSource();
  }
}
