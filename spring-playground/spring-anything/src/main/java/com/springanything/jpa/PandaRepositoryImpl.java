package com.springanything.jpa;

import static com.springanything.jpa.QBamboo.bamboo;
import static com.springanything.jpa.QPanda.panda;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SharedSessionContract;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PandaRepositoryImpl implements PandaRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<PandaResponse> findType(Long id) {
    return Optional.ofNullable(
      queryFactory
        .select(new QPandaResponse(panda.id, panda.name, panda.age, panda.bamboo.type))
        .from(panda)
        .leftJoin(panda.bamboo, bamboo).on(panda.bamboo.id.eq(bamboo.id))
        .fetchOne()
    );
  }

  @Override
  public Optional<Panda> findPandaType(Long id) {
    return Optional.ofNullable(
      queryFactory
        .select(panda)
        .from(panda)
        .leftJoin(panda.bamboo, bamboo)
        .fetchJoin()
        .fetchOne()
    );
  }

  @Override
  public Optional<PandaChild> findChild(Long id) {
    return Optional.ofNullable(
      queryFactory
        .select(new QPandaChild(panda.id, panda.name, panda.age, panda.bamboo))
        .from(panda)
        .leftJoin(panda.bamboo, bamboo).on(panda.bamboo.id.eq(bamboo.id))
        .fetchFirst()
    );
  }

  @Transactional
  public void deleteByBearId(Long bearId, SharedSessionContract session) {
    Session sharedSessionContract = (Session) session;
    sharedSessionContract.joinTransaction();
    queryFactory
      .delete(panda)
      .where(panda.bearId.eq(bearId))
      .execute();
  }
}
