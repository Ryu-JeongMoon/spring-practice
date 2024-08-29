package com.springanything.mapping.nested;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NestedClass {

  private String nestedName;
  private String nestedNickname;
}
