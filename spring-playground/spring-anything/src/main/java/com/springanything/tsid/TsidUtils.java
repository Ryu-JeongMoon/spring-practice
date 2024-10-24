package com.springanything.tsid;

import org.apache.commons.lang3.RandomStringUtils;

import com.github.f4b6a3.tsid.TsidCreator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TsidUtils {

  // 1ms 단위로 정확히 동일 && (1/62)^count 중복 발생
  public static String generate(int count) {
    return TsidCreator.getTsid() + RandomStringUtils.randomAlphanumeric(count);
  }
}
