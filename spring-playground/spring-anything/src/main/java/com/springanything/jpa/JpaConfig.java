package com.springanything.jpa;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {

	private final EntityManager entityManager;

	@Bean
	public JPAQueryFactory queryFactory() {
		return new JPAQueryFactory(entityManager);
	}
}
