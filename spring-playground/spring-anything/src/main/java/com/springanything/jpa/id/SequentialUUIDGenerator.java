package com.springanything.jpa.id;

import java.io.Serial;
import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.fasterxml.uuid.Generators;

public class SequentialUUIDGenerator implements IdentifierGenerator {

  @Serial
  private static final long serialVersionUID = 3993744431409784314L;

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
    return Generators.timeBasedEpochGenerator().generate().toString();
  }
}
