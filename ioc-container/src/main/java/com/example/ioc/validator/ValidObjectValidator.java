package com.example.ioc.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ValidObjectValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return ValidObject.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "notblank", "오노");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "notempty", "오노");
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "notnull", "오노");
  }
}
