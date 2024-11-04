package com.springanything;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @PersistenceContext
  protected EntityManager entityManager;

  @Autowired
  protected WebTestClient webTestClient;

  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  void setUp() {
    webTestClient = MockMvcWebTestClient.bindToApplicationContext(context)
      .build();
  }

  protected void flushAndClear() {
    entityManager.flush();
    entityManager.clear();
  }
}
