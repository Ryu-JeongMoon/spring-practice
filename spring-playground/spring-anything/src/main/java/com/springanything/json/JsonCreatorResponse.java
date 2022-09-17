package com.springanything.json;

import com.fasterxml.jackson.annotation.JsonCreator;

public record JsonCreatorResponse(String name, String email) {

	// @JsonCreator
	// public JsonCreatorResponse(@JsonProperty("NAME") String name, @JsonProperty("EMAIL") String email) {
	// 	this.name = name;
	// 	this.email = email;
	// }

	@JsonCreator
	public JsonCreatorResponse {
	}
}

/*
ref - https://ckddn9496.tistory.com/70
Jackson이 파싱 한 json-deserialize 하기 위해 key-value 얻어와도
이름을 통해서는 어떤 필드에 값을 적용해야 되는지 알 수 없다고 하던디 없어도 되는디?

@JsonCreator
Also note that all JsonProperty annotations must specify actual name (NOT empty String for "default")
unless you use one of extension modules that can detect parameter name;
this because default JDK versions before 8 have not been able to store and/or retrieve parameter names from bytecode.
But with JDK 8 (or using helper libraries such as Paranamer, or other JVM languages like Scala or Kotlin),
specifying name is optional.
 */