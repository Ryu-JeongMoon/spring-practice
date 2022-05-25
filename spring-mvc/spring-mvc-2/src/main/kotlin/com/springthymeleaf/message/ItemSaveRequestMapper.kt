package com.springthymeleaf.message

import org.mapstruct.*

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface ItemSaveRequestMapper {

  fun toDto(entity: Item): ItemSaveRequest
  fun toEntity(dto: ItemSaveRequest): Item
  fun toDtoList(entityList: List<Item>?): List<ItemSaveRequest>?
  fun toEntityList(dtoList: List<ItemSaveRequest>?): List<Item>?

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  fun updateFromDto(dto: ItemSaveRequest, @MappingTarget entity: Item)
}