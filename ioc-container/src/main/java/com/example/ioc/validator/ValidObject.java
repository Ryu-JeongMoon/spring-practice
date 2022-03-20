package com.example.ioc.validator;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class ValidObject {

  @Email
  @NotEmpty
  String email;

  @Size(max = 3)
  @NotBlank
  String name;

  @NotNull
  @Min(0)
  Integer age;
}
