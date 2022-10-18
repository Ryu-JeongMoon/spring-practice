package com.example.inflearnbatch.reader;

import java.util.function.Function;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.util.ClassUtils;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class QuerydslZeroPagingItemReader<T> extends QuerydslPagingItemReader<T> {

  public QuerydslZeroPagingItemReader() {
    super();
    setName(ClassUtils.getShortName(QuerydslZeroPagingItemReader.class));
  }

  public QuerydslZeroPagingItemReader(
    int pageSize,
    EntityManagerFactory emf,
    Function<JPAQueryFactory, JPAQuery<T>> queryFunction
  ) {
    this();
    setTransacted(true);
    setPageSize(pageSize);
    super.emf = emf;
    super.queryFunction = queryFunction;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void doReadPage() {

    EntityTransaction tx = getTxOrNull();

    JPQLQuery<T> query = createQuery()
      .offset(0)
      .limit(getPageSize());

    initResults();

    fetchQuery(query, tx);
  }

}
