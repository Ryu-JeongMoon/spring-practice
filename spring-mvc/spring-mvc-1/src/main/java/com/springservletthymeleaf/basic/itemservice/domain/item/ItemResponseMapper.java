package com.springservletthymeleaf.basic.itemservice.domain.item;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.springservletthymeleaf.basic.itemservice.util.GenericMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemResponseMapper extends GenericMapper<ItemResponse, Item> {
}
