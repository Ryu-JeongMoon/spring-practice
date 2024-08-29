package com.springanything.mapping.nested;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OuterClass {

  private String name;
  private String nickname;
  private InnerClass innerClass;
}
