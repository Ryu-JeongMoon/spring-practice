package com.springanything.circualr;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CircularController {

  private final CircularService circularService;

  @GetMapping("/test/self-invocation")
  public String invokeSelf() {
    return circularService.getBySelf();
  }
}
