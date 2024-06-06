package com.springanything.mvc.validation;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ValidController {

  // UnboundedInput is unbounded.
  // But max http request header size is default 8KB, so boundedInput is bounded to nearly 8KB.
  // It can be overriden by setting server.max-http-request-header-size in application.yml
  // e.g server.max-http-request-header-size: 10KB
  @GetMapping("/input/validation")
  public ValidRequest getValidRequest(@Valid @ModelAttribute ValidRequest validRequest) {
    log.info("Received valid request: {}", validRequest);
    log.info("Received unboundedInput of request: {}", validRequest.unboundedInput().length());

    return validRequest;
  }
}
