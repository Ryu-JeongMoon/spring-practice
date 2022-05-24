package com.springthymeleaf.message

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import javax.validation.Validation

internal class ItemTest {

  private val log = LoggerFactory.getLogger(javaClass)

  @Test
  fun `검증 테스트`() {
    val validatorFactory = Validation.buildDefaultValidatorFactory()
    val validator = validatorFactory.validator

    val item = Item(1L, " ", 500_000_000, 1)
    val constraintViolations = validator.validate(item)

    constraintViolations.forEach {
      run {
        log.info("violations = {}", it)
        log.info("messages = {}", it.message)
      }
    }
  }
}