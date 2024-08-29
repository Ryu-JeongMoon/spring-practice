package com.springanything.mapping.nested;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InnerClass {

  private String innerName;
  private String innerNickname;
  private NestedClass nestedClass;
}
