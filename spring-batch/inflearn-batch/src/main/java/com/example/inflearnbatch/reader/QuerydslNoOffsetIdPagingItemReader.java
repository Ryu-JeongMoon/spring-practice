package com.example.inflearnbatch.reader;

import java.util.function.Function;

import javax.persistence.EntityManagerFactory;

import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import com.example.inflearnbatch.option.QuerydslNoOffsetNumberOptions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class QuerydslNoOffsetIdPagingItemReader<T, N extends Number & Comparable<?>> extends QuerydslPagingItemReader<T> {

  private QuerydslNoOffsetNumberOptions<T, N> options;

  private QuerydslNoOffsetIdPagingItemReader() {
    super();
    setName(ClassUtils.getShortName(QuerydslNoOffsetIdPagingItemReader.class));
  }

  public QuerydslNoOffsetIdPagingItemReader(
    int pageSize,
    EntityManagerFactory emf,
    QuerydslNoOffsetNumberOptions<T, N> options,
    Function<JPAQueryFactory, JPAQuery<T>> queryFunction
  ) {
    super(pageSize, emf, queryFunction);
    setName(ClassUtils.getShortName(QuerydslNoOffsetIdPagingItemReader.class));
    this.options = options;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected void doReadPage() {
    initResults();
    fetchQuery(createQuery().limit(getPageSize()), getTxOrNull());
    resetCurrentIdIfNotLastPage();
  }

  @Override
  protected JPAQuery<T> createQuery() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);
    JPAQuery<T> query = queryFunction.apply(queryFactory);
    options.initKeys(query, getPage()); // 제일 첫번째 페이징시 시작해야할 ID 찾기

    return options.createQuery(query, getPage());
  }

  private void resetCurrentIdIfNotLastPage() {
    if (isNotEmptyResults()) {
      options.resetCurrentId(getLastItem());
    }
  }

  // 조회결과가 Empty이면 results에 null이 담긴다
  private boolean isNotEmptyResults() {
    return !CollectionUtils.isEmpty(results) && results.get(0) != null;
  }

  private T getLastItem() {
    return results.get(results.size() - 1);
  }
}
