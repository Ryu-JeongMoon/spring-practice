package com.springanything.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TriggerService {

  private final TriggerRepository triggerRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public void trigger() {
    Trigger trigger = new Trigger("name");
    Trigger saved = triggerRepository.save(trigger);

    eventPublisher.publishEvent(new TriggerEvent(saved));
  }
}
