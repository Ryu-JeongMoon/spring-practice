package com.springanything.mvc.errors;

import java.util.Collections;
import java.util.List;

public record ExceptionResponse(
  String exception,
  String message,
  List<BindingError> bindingErrors
) {

  public static ExceptionResponse of(String exception, String message) {
    return of(exception, message, Collections.emptyList());
  }

  public static ExceptionResponse of(String exception, String message, List<BindingError> bindingErrors) {
    return new ExceptionResponse(exception, message, bindingErrors);
  }

  public record BindingError(String field, String message) {

  }
}
