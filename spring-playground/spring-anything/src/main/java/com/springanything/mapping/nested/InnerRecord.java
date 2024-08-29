package com.springanything.mapping.nested;

public record InnerRecord(
  String innerName,
  String innerNickname,
  NestedRecord nestedRecord
) {

}
