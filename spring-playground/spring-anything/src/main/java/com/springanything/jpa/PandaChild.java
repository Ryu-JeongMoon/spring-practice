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
	public PandaChild(Long id, String name, int age, Bamboo bamboo) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.type = bamboo.getType();
	}
}

/*
 {
   "id": 3,
   "name": "panda1",
   "age": 1,
   "bamboo": null,
   "weight": 226444392,
   "bearId": null,
   "type": "bear1",
   "doubleAge": 2
 }
 부모 클래스의 @Transient 메서드까지 포함 되버림..?!
 */