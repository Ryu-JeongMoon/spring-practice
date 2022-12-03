package com.springanything.json;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class JsonRequest {

	@JsonUnwrapped
	private JsonMediumResponse mediumResponse;

	@JsonUnwrapped
	private JsonSmallResponse smallResponse;

	public JsonRequest(String name, String nickname, String phone) {
		this.mediumResponse = new JsonMediumResponse(name, nickname);
		this.smallResponse = new JsonSmallResponse(phone);
	}
}
