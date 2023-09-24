package com.springanything.jpa.reader;

import static com.springanything.jpa.QPanda.*;

import java.util.List;
import java.util.function.Function;

import jakarta.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springanything.AbstractRepositoryTest;
import com.springanything.jpa.Panda;
import com.springanything.jpa.PandaRepository;

class QuerydslPagingItemReaderTest extends AbstractRepositoryTest {

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  @Autowired
  private PandaRepository pandaRepository;

  @BeforeEach
  void setUp() {
    pandaRepository.saveAll(List.of(
      Panda.builder().name("panda1").build(),
      Panda.builder().name("panda2").build(),
      Panda.builder().name("panda3").build(),
      Panda.builder().name("panda4").build()
    ));
  }

  @Test
  void fetchQueryResult() throws Exception {
    // given
    Function<JPAQueryFactory, JPAQuery<?>> queryFunction = queryFactory -> queryFactory.from(panda);

    PandaPagingItemReader reader
      = new PandaPagingItemReader(entityManagerFactory, 1, queryFunction);

    // when
    reader.doOpen();
    reader.doReadPage();

    // then
    String item;
    while ((item = reader.read()) != null) {
      log.info("item: {}", item);
    }
  }
}
