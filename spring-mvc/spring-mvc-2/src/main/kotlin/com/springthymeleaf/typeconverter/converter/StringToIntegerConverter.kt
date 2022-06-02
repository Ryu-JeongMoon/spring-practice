package com.springthymeleaf.typeconverter.converter

import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter

class StringToIntegerConverter : Converter<String, Int> {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun convert(source: String): Int {
    log.info("source = {}", source)
    return source.toInt() as? Int ?: throw IllegalArgumentException()
  }
}