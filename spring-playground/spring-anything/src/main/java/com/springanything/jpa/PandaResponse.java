package com.springanything.jpa;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;

public record PandaResponse(
	Long id,
	String name,
	int age,
	String type
) {

	@Builder
	@QueryProjection
	public PandaResponse(Long id, String name, int age, String type) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.type = type;
	}

	@QueryProjection
	public PandaResponse(Long id, String name, int age, Bear bear) {
		this(id, name, age, bear.getType());
	}
}
