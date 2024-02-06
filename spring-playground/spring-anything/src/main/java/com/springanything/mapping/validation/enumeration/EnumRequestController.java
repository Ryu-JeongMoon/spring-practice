package com.springanything.mapping.validation.enumeration;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnumRequestController {

  @GetMapping("/test/enum-request")
  public EnumRequest mapEnumRequest(@Validated EnumRequest request) {
    return request;
  }
}
