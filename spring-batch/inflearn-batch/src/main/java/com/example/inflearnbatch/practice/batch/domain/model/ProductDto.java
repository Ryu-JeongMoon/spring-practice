package com.example.inflearnbatch.practice.batch.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private Request request;
	private Response response;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Request {

		private Long id;
		private String name;
		private int price;
		private String type;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {

		private Long id;
		private String name;
		private int price;
		private String type;
	}
}
