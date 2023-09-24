package com.springanything.jpa.reader;

import static com.springanything.jpa.QPanda.panda;

import java.util.List;
import java.util.function.Function;

import jakarta.persistence.EntityManagerFactory;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class PandaPagingItemReader extends QuerydslPagingItemReader<String> {

  public PandaPagingItemReader(
    EntityManagerFactory entityManagerFactory,
    int pageSize,
    Function<JPAQueryFactory, JPAQuery<?>> queryFunction
  ) {
    super(entityManagerFactory, pageSize, queryFunction);
  }

  @Override
  public int getPage() {
    return 0;
  }

  @Override
  public List<String> fetchQueryResult(JPAQuery<?> query) {
    return query.select(panda.name).fetch()
      .stream()
      .map(String::valueOf)
      .toList();
  }
}
