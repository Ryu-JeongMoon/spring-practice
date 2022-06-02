package com.springthymeleaf.typeconverter.formatter

import org.slf4j.LoggerFactory
import org.springframework.format.Formatter
import java.text.NumberFormat
import java.util.*

class NumberFormatter : Formatter<Number> {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun parse(text: String, locale: Locale): Number {
    log.info("text = {}, locale = {}", text, locale)
    return NumberFormat.getInstance(locale).parse(text)
  }

  override fun print(`object`: Number, locale: Locale): String {
    log.info("object = {}, locale = {}", `object`, locale)
    return NumberFormat.getInstance(locale).format(`object`)
  }
}