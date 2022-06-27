package com.springanything.jpa;

import static com.springanything.jpa.QPanda.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

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
				.select(new QPandaResponse(panda.id, panda.name, panda.age, panda.bear))
				.from(panda)
				.leftJoin(panda.bear, QBear.bear)
				.on(panda.bear.id.eq(QBear.bear.id))
				.fetchOne()
		);
	}

	@Override
	public Optional<Panda> findPandaType(Long id) {
		return Optional.ofNullable(
			queryFactory
				.select(panda)
				.from(panda)
				.leftJoin(panda.bear, QBear.bear)
				.fetchJoin()
				.fetchOne()
		);
	}

	@Override
	public Optional<PandaChild> findChild(Long id) {
		return Optional.ofNullable(
			queryFactory
				.select(new QPandaChild(panda.id, panda.name, panda.age, panda.bear))
				.from(panda)
				.leftJoin(panda.bear, QBear.bear)
				.on(panda.bear.id.eq(QBear.bear.id))
				.fetchOne()
		);
	}
}
