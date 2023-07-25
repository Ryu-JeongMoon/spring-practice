package com.springanything;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.springanything.config.TestConfig;
import com.springanything.config.p6spy.P6spyConfig;

@DataJpaTest
@Import({ TestConfig.class, P6spyConfig.class })
public abstract class AbstractRepositoryTest {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @PersistenceContext
  protected EntityManager entityManager;

  protected void flushAndClear() {
    entityManager.flush();
    entityManager.clear();
  }
}
