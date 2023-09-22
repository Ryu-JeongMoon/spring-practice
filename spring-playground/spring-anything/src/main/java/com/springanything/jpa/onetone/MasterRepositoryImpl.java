package com.springanything.jpa.onetone;

import static com.springanything.jpa.onetone.QMaster.master;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MasterRepositoryImpl implements MasterRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public void deleteByIds(List<Long> ids) {
    queryFactory.delete(master)
      .where(master.id.in(ids))
      .execute();
  }
}
