package com.springanything.mapping.validation.string;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

public record VariableStringRequest(
  @Size(max = 255)
  String name1,
  @Max(255)
  String name2
) {

}
