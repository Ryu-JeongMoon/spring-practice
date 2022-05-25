package com.springthymeleaf.message

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ItemResponseTest {

  private val log = LoggerFactory.getLogger(javaClass)

  @Autowired
  lateinit var itemResponseMapper: ItemResponseMapper

  @Test
  @DisplayName("DTO 변환 과연?!")
  fun yahoo() {
    val item = Item(1L, "panda", 5000, 5000)
    val itemResponse = itemResponseMapper.toDto(item)

    assertAll(
      { assertThat(itemResponse.id).isEqualTo(item.id) },
      { assertThat(itemResponse.name).isEqualTo(item.name) },
      { assertThat(itemResponse.price).isEqualTo(item.price) },
      { assertThat(itemResponse.quantity).isEqualTo(item.quantity) }
    )
  }
}