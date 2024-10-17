package com.springanything.event;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TriggerController {

  private final TriggerService triggerService;

  @GetMapping("/events/trigger")
  public void trigger() {
    triggerService.trigger();
  }
}
