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
	public PandaResponse {
	}
}
