package com.springanything.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TriggerEventListener {

  private final TriggerRepository triggerRepository;

  @Async
  @Transactional
  @EventListener
  public void onTriggerEvent(TriggerEvent triggerEvent) {
    Trigger source = triggerEvent.getSource();
    source.setName("new name");
    triggerRepository.save(source);
  }

  @Async
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
  public void onTransactionalTriggerEvent(TriggerEvent triggerEvent) {
    Trigger source = triggerEvent.getSource();
    Trigger trigger = triggerRepository.findById(source.getId()).orElseThrow();
    trigger.setName("transactional new name");
  }
}
