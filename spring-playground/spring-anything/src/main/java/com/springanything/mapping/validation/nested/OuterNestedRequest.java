package com.springanything.mapping.validation.nested;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class OuterNestedRequest {

  @NotBlank
  @Length(max = 255)
  private String outerName;

  @NotNull
  private boolean outerBoolean;

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
