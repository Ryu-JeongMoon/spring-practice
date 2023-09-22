package com.springanything.mvc.accessor;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class AccessorRequest {

  /**
   * setter 있어도 @Accessors(fluent = true) 있으면 인식 불가
   */
  @Setter
  @Accessors(fluent = true)
  private String isPanda;
}
