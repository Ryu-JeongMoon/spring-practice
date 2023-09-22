package com.springanything.mvc.errors;

import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springanything.mapping.validation.string.VariableStringController;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @Nonnull
  private String getExceptionClass(Exception e) {
    return e.getClass().getName();
  }

  @ExceptionHandler(value = {
    IllegalArgumentException.class, PersistenceException.class,
    JsonProcessingException.class, ConstraintViolationException.class, ArithmeticException.class
  })
  public ResponseEntity<ExceptionResponse> badRequestHandler(Exception e) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(ExceptionResponse.of(getExceptionClass(e), e.getMessage()));
  }

  /**
   * If the input is too huge, the exception message will be too long.
   * So should not respond exception.getMessage() for invalid input.
   * <p>
   * See {@link VariableStringController#mapVariableString}
   */
  @ExceptionHandler(value = { BindException.class, MethodArgumentNotValidException.class })
  public ResponseEntity<ExceptionResponse> mappingExceptionHandler(BindException e) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(ExceptionResponse.of(getExceptionClass(e), "invalid data mapping", getBindingErrors(e)));
  }

  /**
   * {@link MethodArgumentNotValidException} extends {@link BindException} from Spring 5.3
   */
  @Nonnull
  private List<ExceptionResponse.BindingError> getBindingErrors(BindException e) {
    return e.getBindingResult().getAllErrors()
      .stream()
      .map(error -> {
        FieldError fieldError = (FieldError) error;
        String defaultMessage = StringUtils.defaultString(fieldError.getDefaultMessage());
        return new ExceptionResponse.BindingError(fieldError.getField(), defaultMessage);
      })
      .toList();
  }
}
