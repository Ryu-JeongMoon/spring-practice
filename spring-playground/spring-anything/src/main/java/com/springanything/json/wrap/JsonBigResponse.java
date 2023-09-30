package com.springanything.json.wrap;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString
public class JsonBigResponse {

  @JsonUnwrapped
  private JsonMediumResponse mediumResponse;

  @JsonUnwrapped
  private JsonSmallResponse smallResponse;
}
