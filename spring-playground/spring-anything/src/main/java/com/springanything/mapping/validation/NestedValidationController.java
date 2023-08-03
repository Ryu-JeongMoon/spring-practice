package com.springanything.mapping.validation;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NestedValidationController {

  @PostMapping("/test/nested-validation")
  public OuterNestedRequest testNestedValidation(@Valid @RequestBody OuterNestedRequest request) {
    return request;
  }
}
