package com.springanything.tsid;

import java.io.Serial;
import java.io.Serializable;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.github.f4b6a3.tsid.TsidCreator;

public class TimeSortedIdGenerator implements IdentifierGenerator {

  @Serial
  private static final long serialVersionUID = -7811006710786861310L;

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object) {
    // 1ms 단위로 정확히 동일 && 1 / 62^3으로 중복 발생
    return TsidCreator.getTsid() + RandomStringUtils.randomAlphanumeric(3);
  }
}
