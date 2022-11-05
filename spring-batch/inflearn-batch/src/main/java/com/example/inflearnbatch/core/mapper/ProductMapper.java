package com.example.inflearnbatch.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.example.inflearnbatch.practice.batch.domain.Product;
import com.example.inflearnbatch.practice.batch.domain.model.ProductDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper extends GenericMapper<ProductDto.Request, Product> {

}
