package com.springthymeleaf.message

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource
import java.util.*

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MessageSourceTest {

  private val log = LoggerFactory.getLogger(javaClass)

  @Autowired
  lateinit var ms: MessageSource

  @Test
  @DisplayName("locale 정보 없다면 기본 메시지 선택")
  fun hello() {
    val message = ms.getMessage("hello", null, Locale.CHINA)

    assertAll(
      { assertThat(message).isNotNull() },
      { assertThat(message).isEqualTo("hello") }
    )
  }

  @Test
  @DisplayName("로케일 별 설정도 오께이")
  fun helloInKorean() {
    val message = ms.getMessage("hello", null, Locale.KOREAN)

    assertAll(
      { assertThat(message).isNotNull() },
      { assertThat(message).isEqualTo("안뇽") }
    )
  }
}

/*
messages_ko.properties를 기본 설정으로 하기 위해 messages.properties로 설정해놨는데 얘를 자꾸 못 찾는 버그가 있었다리
이건 뭐가 문제일꼬?, 아래와 같이 설정해두면 문제 없이 돌아감
messages.properties -> 영어
messages_ko.properties -> 한국어
 */