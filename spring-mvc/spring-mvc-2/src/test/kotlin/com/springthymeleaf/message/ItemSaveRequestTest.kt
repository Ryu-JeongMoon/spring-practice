package com.springthymeleaf.message

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ItemSaveRequestTest {

  private val log = LoggerFactory.getLogger(javaClass)

  @Autowired
  lateinit var itemSaveRequestMapper: ItemSaveRequestMapper

  @Test
  @DisplayName("업데이트 과연 ?!")
  fun updateFromDto() {
    val item = Item(1L, "panda", 5000, 5000)
    val itemSaveRequest = ItemSaveRequest(null, "bear", 3000, 3000)
    itemSaveRequestMapper.updateFromDto(itemSaveRequest, item)

    assertAll(
      { assertThat(item.id).isEqualTo(1L) },
      { assertThat(item.name).isEqualTo(itemSaveRequest.name) },
      { assertThat(item.price).isEqualTo(itemSaveRequest.price) },
      { assertThat(item.quantity).isEqualTo(itemSaveRequest.quantity) }
    )
  }
}