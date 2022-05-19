package com.springthymeleaf.message

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService(
  val itemRepository: ItemRepository,
  val itemRequestMapper: ItemRequestMapper,
  val itemResponseMapper: ItemResponseMapper,
) {

  @Transactional
  fun edit(itemId: Long, itemRequest: ItemRequest) {
    val item = itemRepository.findById(itemId).orElseThrow()
    itemRequestMapper.updateFromDto(itemRequest, item)
  }
}