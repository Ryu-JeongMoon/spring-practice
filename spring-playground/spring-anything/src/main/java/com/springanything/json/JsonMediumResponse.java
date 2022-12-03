package com.springanything.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class JsonMediumResponse {

	@JsonProperty("NAME")
	private Name name;

	@JsonProperty("NICKNAME")
	private String nickname;

	public JsonMediumResponse(String name, String nickname) {
		this.name = Name.valueOf(name);
		this.nickname = nickname;
	}
}
