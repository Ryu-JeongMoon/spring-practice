package com.springthymeleaf.exception.controller

import com.springthymeleaf.exception.CustomException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(annotations = [RestController::class])
class ApiExceptionHandler {

  private val log = LoggerFactory.getLogger(javaClass)

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException::class)
  fun illegalExHandle(e: IllegalArgumentException): ErrorResult? {
    log.error("[exceptionHandle] ex", e)
    return ErrorResult("BAD", e.message)
  }

  @ExceptionHandler
  fun userExHandle(e: CustomException): ResponseEntity<ErrorResult?>? {
    log.error("[exceptionHandle] ex", e)
    val errorResult = ErrorResult("USER-EX", e.message)
    return ResponseEntity(errorResult, HttpStatus.BAD_REQUEST)
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler
  fun exHandle(e: Exception?): ErrorResult? {
    log.error("[exceptionHandle] ex", e)
    return ErrorResult("EX", "내부 오류")
  }
}

data class ErrorResult(val code: String?, val message: String?) {
}