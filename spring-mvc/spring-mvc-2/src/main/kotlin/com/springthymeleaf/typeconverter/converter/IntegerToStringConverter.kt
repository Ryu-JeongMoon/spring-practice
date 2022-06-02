package com.springthymeleaf.typeconverter.converter

import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter

class IntegerToStringConverter: Converter<Int, String> {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun convert(source: Int): String {
    log.info("source = {}", source)
    return source.toString()
  }
}