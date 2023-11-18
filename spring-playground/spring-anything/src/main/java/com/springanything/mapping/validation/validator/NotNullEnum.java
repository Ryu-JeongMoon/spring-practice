package com.springanything.mapping.validation.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface NotNullEnum {

  String message() default "{jakarta.validation.constraints.NotNull.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
