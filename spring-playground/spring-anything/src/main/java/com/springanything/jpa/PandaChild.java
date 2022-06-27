package com.springanything.jpa;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class PandaChild extends Panda {

	private String type;

	@QueryProjection
	public PandaChild(Long id, String name, int age, Bear bear) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.type = bear.getType();
	}
}
