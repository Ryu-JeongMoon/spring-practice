package com.springanything.mapping.nested;

import static com.springanything.mapping.nested.QNestedSetterRequest.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NestedSetterRequestRepoImpl implements NestedSetterRequestRepoCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<NestedSetterRequest> findByInnerName(String name) {
		return Optional.ofNullable(
			queryFactory.selectFrom(nestedSetterRequest)
				.where(nestedSetterRequest.innerRequest.name.eq(name))
				.fetchOne()
		);
	}
}
