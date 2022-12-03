package com.example.inflearnbatch.practice.batch.chunk.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.inflearnbatch.core.mapper.ProductMapper;
import com.example.inflearnbatch.practice.batch.domain.Product;
import com.example.inflearnbatch.practice.batch.domain.model.ProductDto;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileItemProcessor implements ItemProcessor<ProductDto.Request, Product> {

	private final ProductMapper productMapper;

	@Override
	public Product process(ProductDto.Request item) {
		return productMapper.toEntity(item);
	}
}
