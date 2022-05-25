package com.springthymeleaf.validation

import com.springthymeleaf.message.ItemSaveRequest
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Component
class ItemValidator : Validator {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun supports(clazz: Class<*>): Boolean {
    return clazz.isAssignableFrom(ItemSaveRequest::class.java)
  }

  override fun validate(target: Any, errors: Errors) {
    log.info("object name => {}", errors.objectName)
    target as ItemSaveRequest

    if (!StringUtils.hasText(target.name)) {
      errors.rejectValue(
        "name",
        "required"
      )
    }

    if (target.price == null || target.price!! < 1000 || target.price!! > 1000000) {
      errors.rejectValue(
        "price",
        "range",
        arrayOf(1_000, 1_000_000),
        null
      )
    }

    if (target.quantity == null || target.quantity!! >= 9999 || target.quantity!! < 10) {
      errors.rejectValue(
        "quantity",
        "max",
        arrayOf(9_999),
        null
      )
    }

    if (target.price != null && target.quantity != null) {
      val resultPrice: Int = target.price!! * target.quantity!!
      if (resultPrice < 10000) {
        errors.rejectValue(
          "price",
          "totalPriceMin",
          arrayOf(10_000, resultPrice),
          null
        )
      }
    }
  }
}