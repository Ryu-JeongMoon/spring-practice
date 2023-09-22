package com.springanything.jpa.base;

import java.io.Serializable;

public abstract class BaseEntity<PK extends Serializable> implements Serializable {

  public abstract PK getId();
}
