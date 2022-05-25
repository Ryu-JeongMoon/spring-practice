package com.springthymeleaf.message

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ItemRequestTest {

  private val log = LoggerFactory.getLogger(javaClass)

  @Autowired
  lateinit var itemRequestMapper: ItemRequestMapper

  @Test
  @DisplayName("업데이트 과연 ?!")
  fun updateFromDto() {
    val item = Item(1L, "panda", 5000, 5000)
    val itemRequest = ItemRequest(null, "bear", 3000, 3000)
    itemRequestMapper.updateFromDto(itemRequest, item)

    assertAll(
      { assertThat(item.id).isEqualTo(1L) },
      { assertThat(item.name).isEqualTo(itemRequest.name) },
      { assertThat(item.price).isEqualTo(itemRequest.price) },
      { assertThat(item.quantity).isEqualTo(itemRequest.quantity) }
    )
  }
}