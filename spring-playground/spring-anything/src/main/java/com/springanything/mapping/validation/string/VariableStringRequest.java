package com.springanything.mapping.validation.string;

import jakarta.validation.constraints.Size;

public record VariableStringRequest(@Size(max = 255) String name) {

}
