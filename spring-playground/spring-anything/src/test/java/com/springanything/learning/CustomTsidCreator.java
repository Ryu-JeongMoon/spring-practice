package com.springanything.learning;

import net.bytebuddy.utility.RandomString;

import com.github.f4b6a3.tsid.TsidCreator;

class CustomTsidCreator {

  public String create() {
    // 1ms 단위로 정확히 동일 && 1 / 62^3으로 중복 발생
    return TsidCreator.getTsid() + RandomString.make(3);
  }
}
