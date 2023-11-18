package com.springanything.mapping.validation.enumeration;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnumRequestController {

  @GetMapping("/test/enum-request")
  public Bears mapEnumRequest(@Valid EnumRequest request) {
    return request.bears();
  }
}
