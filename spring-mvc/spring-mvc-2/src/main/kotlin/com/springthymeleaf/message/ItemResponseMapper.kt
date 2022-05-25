package com.springthymeleaf.message

import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ItemResponseMapper {

  fun toDto(entity: Item): ItemResponse
  fun toEntity(dto: ItemResponse): Item
  fun toDtoList(entityList: List<Item>?): List<ItemResponse>?
  fun toEntityList(dtoList: List<ItemResponse>?): List<Item>?
}