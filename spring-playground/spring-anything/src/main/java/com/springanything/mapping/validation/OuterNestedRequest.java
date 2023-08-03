package com.springanything.mapping.validation;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class OuterNestedRequest {

  @NotBlank
  private String outerName;

  /**
   * If you want to validate nested object, use @Valid annotation.
   * It can be used on Controller, also can be used on Class field.
   */
  @Valid
  @NotEmpty
  private List<InnerNestedRequest> innerNestedRequests;

  @Data
  public static class InnerNestedRequest {

    @Pattern(regexp = "[a-z]{3,10}")
    private String innerName;
  }
}
