package com.springthymeleaf.learning

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.slf4j.LoggerFactory
import org.springframework.validation.DefaultMessageCodesResolver
import org.springframework.validation.MessageCodesResolver

internal class MessageCodesResolverTest {

  private val log = LoggerFactory.getLogger(javaClass)

  private val messageCodesResolver: MessageCodesResolver = DefaultMessageCodesResolver()

  @Test
  @DisplayName("에러 코드 메시지")
  fun objectTest() {
    // given
    val messageCodes = messageCodesResolver.resolveMessageCodes("required", "item")

    // then
    log.info("message codes = {}", messageCodes)

    assertAll(
      { assertThat(messageCodes).contains("required") },
      { assertThat(messageCodes).contains("required.item") }
    )
  }

  @Test
  @DisplayName("에러 코드 메시지 - 필드")
  fun filedTest() {
    // given
    val messageCodes = messageCodesResolver.resolveMessageCodes(
      "required",
      "itemRequest",
      "name",
      String::class.java
    )

    // when
    log.info("message codes = {}", messageCodes)

    // then
    assertAll(
      { assertThat(messageCodes).contains("required.itemRequest.name") },
      { assertThat(messageCodes).contains("required.name") },
      { assertThat(messageCodes).contains("required.java.lang.String") },
      { assertThat(messageCodes).contains("required") },
    )
  }
}

/*
1 -> required.itemRequest.name
2 -> required.name
3 -> required + type (required.java.lang.String)
4 -> required (범용적으로 사용)
가장 세밀한 부분부터 범용적인 부분까지 스캔하여 없으면 다음 계층의 메세지를 반환
 */