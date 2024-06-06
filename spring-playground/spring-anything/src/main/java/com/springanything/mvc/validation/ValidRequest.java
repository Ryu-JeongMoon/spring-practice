package com.springanything.mvc.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ValidRequest(
  @Size(max = 255) String boundedInput,
  @NotBlank String unboundedInput
) {

}
