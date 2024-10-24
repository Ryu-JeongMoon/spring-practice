package com.springanything.tsid;

import java.io.Serial;
import java.io.Serializable;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class TimeSortedIdGenerator implements IdentifierGenerator {

  @Serial
  private static final long serialVersionUID = -7811006710786861310L;

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object) {
    return TsidUtils.generate(3);
  }
}
