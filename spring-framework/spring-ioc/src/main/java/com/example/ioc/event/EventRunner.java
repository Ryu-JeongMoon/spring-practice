package com.example.ioc.event;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventRunner implements ApplicationRunner {

  private final ApplicationEventPublisher publisher;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    publisher.publishEvent(new NewEvent(this, 900));
    publisher.publishEvent(new OldEvent(this, 500));
  }
}
