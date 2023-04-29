package com.springanything.learning;

import net.bytebuddy.utility.RandomString;

import com.github.f4b6a3.tsid.TsidCreator;

class CustomTsidCreator {

  public String create() {
    return TsidCreator.getTsid() + RandomString.make(3);
  }
}
