package com.springthymeleaf.login.web.annotation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.slf4j.LoggerFactory

internal class LoginMemberArgumentResolverTest {

  private val log = LoggerFactory.getLogger(javaClass)

  @Test
  fun `값이 null인 경우 그대로 반환`() {
    assertAll(
      { assertThat(returnValue(null)).isNull() },
      { assertThat(returnValue("panda")).isNotNull }
    )
  }

  private fun returnValue(input: String?): String? {
    return input?.let { "$it bear" }
  }
}

