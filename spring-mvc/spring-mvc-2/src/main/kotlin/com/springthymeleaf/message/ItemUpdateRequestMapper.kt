package com.springthymeleaf.message

import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ItemUpdateRequestMapper {

  fun toDto(entity: Item): ItemUpdateRequest
  fun toEntity(dto: ItemUpdateRequest): Item
  fun toDtoList(entityList: List<Item>?): List<ItemUpdateRequest>?
  fun toEntityList(dtoList: List<ItemUpdateRequest>?): List<Item>?

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  fun updateFromDto(dto: ItemUpdateRequest, @MappingTarget entity: Item)
}