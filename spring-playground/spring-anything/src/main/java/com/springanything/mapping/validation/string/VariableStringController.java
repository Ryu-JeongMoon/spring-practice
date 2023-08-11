package com.springanything.mapping.validation.string;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VariableStringController {

  @PostMapping("/test/variable-string")
  public int mapVariableString(@Valid @RequestBody VariableStringRequest request) {
    return request.name().length();
  }
}
