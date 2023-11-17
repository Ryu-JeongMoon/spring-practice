package com.springanything.jpa.com.high_performance.persistence;

import org.junit.jupiter.api.Test;

import com.springanything.AbstractRepositoryTest;

class LongIdPostTest extends AbstractRepositoryTest {

  @Test
  void persistLongId() {
    // given
    LongIdPost longIdPost = new LongIdPost();
    longIdPost.setTitle("High-Performance Java Persistence");

    // when type of generated id value is auto / identity in mysql kind
    // this 'persist' only generates the identifier value
    // it's not flushed yet to the database
    // SELECT NEXT VALUE FOR LONG_ID_POST_SEQ
    entityManager.persist(longIdPost);
    log.info("post entity identifier is {}", longIdPost.getId());

    // insert query is executed after flush trigger like em.flush(), jpql query, etc
    flushAndClear();
  }

  @Test
  void persistTimeSortedId() {
    // given
    TimeSortedIdPost timeSortedIdPost = new TimeSortedIdPost();
    timeSortedIdPost.setTitle("High-Performance Java Persistence");

    // time sorted id generator is executed in application
    entityManager.persist(timeSortedIdPost);
    log.info("post entity identifier is {}", timeSortedIdPost.getId());

    // then
    flushAndClear();
  }
}
