package com.springanything.mapping.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.springanything.mapping.validation.NotNullEnum;

public class EnumValidator implements ConstraintValidator<NotNullEnum, Enum<?>> {

  @Override
  public boolean isValid(Enum value, ConstraintValidatorContext context) {
    return value != null;
  }
}
