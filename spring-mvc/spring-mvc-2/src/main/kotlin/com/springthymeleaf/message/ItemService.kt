package com.springthymeleaf.message

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemService(
  val itemRepository: ItemRepository,
  val itemSaveRequestMapper: ItemSaveRequestMapper,
  val itemUpdateRequestMapper: ItemUpdateRequestMapper,
  val itemResponseMapper: ItemResponseMapper,
) {

  /* 호환성을 위해 남겨둔 구 버전 */
  @Transactional
  fun edit(itemId: Long, itemSaveRequest: ItemSaveRequest) {
    val item = itemRepository.findById(itemId).orElseThrow()
    itemSaveRequestMapper.updateFromDto(itemSaveRequest, item)
  }

  @Transactional
  fun edit(itemId: Long, itemUpdateRequest: ItemUpdateRequest) {
    val item = itemRepository.findById(itemId).orElseThrow()
    itemUpdateRequestMapper.updateFromDto(itemUpdateRequest, item)
  }
}