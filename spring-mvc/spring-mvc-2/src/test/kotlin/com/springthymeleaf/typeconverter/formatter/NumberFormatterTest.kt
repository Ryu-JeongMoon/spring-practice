package com.springthymeleaf.typeconverter.formatter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import java.util.*

internal class NumberFormatterTest {

  private val numberFormatter = NumberFormatter()
  private val log = LoggerFactory.getLogger(javaClass)

  @Test
  fun parse() {
    val result = numberFormatter.parse("1,000", Locale.KOREA)
    log.info("result = {}", result)

    assertThat(result).isEqualTo(1000L)
  }

  @Test
  fun print() {
    val result = numberFormatter.print(1000, Locale.KOREA)
    log.info("result = {}", result)

    assertThat(result).isEqualTo("1,000")
  }
}