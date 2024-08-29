package com.springanything.mapping.nested;

public record OuterRecord(
  String name,
  String nickname,
  InnerRecord innerRecord
) {

}
