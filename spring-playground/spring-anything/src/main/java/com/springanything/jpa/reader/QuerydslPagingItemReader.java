package com.springanything.jpa.reader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public abstract class QuerydslPagingItemReader<T> extends AbstractPagingItemReader<T> {

  protected final Map<String, Object> jpaPropertyMap = new HashMap<>();
  protected final Function<JPAQueryFactory, JPAQuery<?>> queryFunction;
  protected final EntityManagerFactory entityManagerFactory;
  protected EntityManager entityManager;
  protected boolean transacted;

  public QuerydslPagingItemReader(
    EntityManagerFactory entityManagerFactory,
    int pageSize,
    Function<JPAQueryFactory, JPAQuery<?>> queryFunction
  ) {
    setName(ClassUtils.getShortName(QuerydslPagingItemReader.class));
    setPageSize(pageSize);
    this.transacted = true;

    this.entityManagerFactory = entityManagerFactory;
    this.queryFunction = queryFunction;
  }

  @Override
  protected void doOpen() throws Exception {
    super.doOpen();

    entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap);
    if (entityManager == null) {
      throw new DataAccessResourceFailureException("Unable to obtain an EntityManager");
    }
  }

  @Override
  protected void doReadPage() {
    clearIfTransacted();

    JPAQuery<?> query = createQuery()
      .offset((long) getPage() * getPageSize())
      .limit(getPageSize());

    initResults();

    fetchQuery(query, getTxOrNull());
  }

  private void clearIfTransacted() {
    if (transacted) {
      entityManager.clear();
    }
  }

  protected JPAQuery<?> createQuery() {
    return queryFunction.apply(new JPAQueryFactory(() -> entityManager));
  }

  private void initResults() {
    if (CollectionUtils.isEmpty(results)) {
      results = new CopyOnWriteArrayList<>();
    } else {
      results.clear();
    }
  }

  private void fetchQuery(JPAQuery<?> query, EntityTransaction tx) {
    if (transacted) {
      results.addAll(fetchQueryResult(query));
      if (tx != null) {
        tx.commit();
      }
    } else {
      for (T entity : fetchQueryResult(query)) {
        entityManager.detach(entity);
        results.add(entity);
      }
    }
  }

  protected EntityTransaction getTxOrNull() {
    if (transacted) {
      EntityTransaction tx = entityManager.getTransaction();
      tx.begin();

      entityManager.flush();
      entityManager.clear();
      return tx;
    }

    return null;
  }

  public abstract List<T> fetchQueryResult(JPAQuery<?> query);

  @Override
  protected void doClose() throws Exception {
    entityManager.close();
    super.doClose();
  }
}
