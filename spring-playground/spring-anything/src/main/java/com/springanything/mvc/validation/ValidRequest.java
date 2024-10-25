package com.springanything.mvc.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

public record ValidRequest(
  @Size(max = 255)
  String boundedInput,
  @NotBlank
  String unboundedInput,
  @URL
  String url
) {

}
