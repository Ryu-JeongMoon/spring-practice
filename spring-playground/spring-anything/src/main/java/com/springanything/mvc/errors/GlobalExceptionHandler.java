package com.springanything.mvc.errors;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

	private static String getExceptionClass(Exception e) {
		return e != null ? e.getClass().getName() : "UnknownException";
	}

	@ExceptionHandler(value = {
		IllegalArgumentException.class, PersistenceException.class,
		JsonProcessingException.class, ConstraintViolationException.class, ArithmeticException.class
	})
	public ResponseEntity<ExceptionResponse> badRequestHandler(Exception e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.of(getExceptionClass(e), e.getMessage()));
	}

	@ExceptionHandler(value = {
		BindException.class
	})
	public ResponseEntity<ExceptionResponse> bindExceptionHandler(Exception e) {
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ExceptionResponse.of(getExceptionClass(e), e.getMessage()));
	}
}
