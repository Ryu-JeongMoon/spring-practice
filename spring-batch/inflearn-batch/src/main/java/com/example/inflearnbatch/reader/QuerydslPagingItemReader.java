package com.example.inflearnbatch.reader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class QuerydslPagingItemReader<T> extends AbstractPagingItemReader<T> {

  protected final Map<String, Object> jpaPropertyMap = new HashMap<>();

  protected boolean transacted = true;
  protected EntityManager em;
  protected EntityManagerFactory emf;
  protected Function<JPAQueryFactory, JPAQuery<T>> queryFunction;

  protected QuerydslPagingItemReader() {
    setName(ClassUtils.getShortName(QuerydslPagingItemReader.class));
  }

  public QuerydslPagingItemReader(
    int pageSize,
    EntityManagerFactory emf,
    Function<JPAQueryFactory, JPAQuery<T>> queryFunction
  ) {
    this();
    setPageSize(pageSize);
    this.emf = emf;
    this.queryFunction = queryFunction;
  }

  public void setTransacted(boolean transacted) {
    this.transacted = transacted;
  }

  @Override
  protected void doOpen() throws Exception {
    super.doOpen();

    em = emf.createEntityManager(jpaPropertyMap);
    if (em == null) {
      throw new DataAccessResourceFailureException("Unable to obtain an EntityManager");
    }
  }

  @Override
  protected void doReadPage() {
    clearIfTransacted();

    JPAQuery<T> query = createQuery()
      .offset((long) getPage() * getPageSize())
      .limit(getPageSize());

    initResults();

    fetchQuery(query, getTxOrNull());
  }

  protected void clearIfTransacted() {
    if (transacted) {
      em.clear();
    }
  }

  protected EntityTransaction getTxOrNull() {
    if (transacted) {
      EntityTransaction tx = em.getTransaction();
      tx.begin();

      em.flush();
      em.clear();
      return tx;
    }

    return null;
  }

  protected JPAQuery<T> createQuery() {
    JPAQueryFactory queryFactory = new JPAQueryFactory(em);
    return queryFunction.apply(queryFactory);
  }

  protected void initResults() {
    if (CollectionUtils.isEmpty(results)) {
      results = new CopyOnWriteArrayList<>();
    } else {
      results.clear();
    }
  }

  protected void fetchQuery(JPQLQuery<T> query, EntityTransaction tx) {
    if (transacted) {
      results.addAll(query.fetch());
      if (tx != null) {
        tx.commit();
      }
    } else {
      List<T> queryResult = query.fetch();
      for (T entity : queryResult) {
        em.detach(entity);
        results.add(entity);
      }
    }
  }

  @Override
  protected void doJumpToPage(int itemIndex) {
  }

  @Override
  protected void doClose() throws Exception {
    em.close();
    super.doClose();
  }
}
