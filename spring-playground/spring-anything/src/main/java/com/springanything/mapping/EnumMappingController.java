package com.springanything.mapping;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnumMappingController {

  @PostMapping("/mapping/enum")
  public EnumRequest map(@Valid @RequestBody EnumRequest request) {
    return request;
  }

  private record EnumRequest(
    @NotBlank
    String name,
    EnumType enumType
  ) {

    private enum EnumType {
      ENUM1, ENUM2, ENUM3
    }
  }
}
