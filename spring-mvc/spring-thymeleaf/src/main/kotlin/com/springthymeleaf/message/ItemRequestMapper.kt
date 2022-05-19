package com.springthymeleaf.message

import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ItemRequestMapper {

  fun toDto(entity: Item): ItemRequest
  fun toEntity(dto: ItemRequest): Item
  fun toDtoList(entityList: List<Item>?): List<ItemRequest>?
  fun toEntityList(dtoList: List<ItemRequest>?): List<Item>?

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  fun updateFromDto(dto: ItemRequest, @MappingTarget entity: Item)
}