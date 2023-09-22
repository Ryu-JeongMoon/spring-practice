package com.springanything;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public abstract class AbstractIntegrationTest {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @PersistenceContext
  protected EntityManager entityManager;

  protected void flushAndClear() {
    entityManager.flush();
    entityManager.clear();
  }
}
