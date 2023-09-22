package com.springanything.util;

import org.springframework.lang.NonNull;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CaseUtils {

  public static String toCamelCase(@NonNull String value) {
    return value.contains("_")
      ? org.apache.commons.text.CaseUtils.toCamelCase(value, false, '_')
      : convertFirstLetterToLowerCase(value);
  }

  private static String convertFirstLetterToLowerCase(String value) {
    char firstCharacter = value.charAt(0);
    if (Character.isLowerCase(firstCharacter)) {
      return value;
    }

    return Character.toLowerCase(firstCharacter) + value.substring(1);
  }
}
