package com.springservletthymeleaf.basic.itemservice.domain.item;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.springservletthymeleaf.basic.itemservice.util.GenericMapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemRequestMapper extends GenericMapper<ItemRequest, Item> {
}
