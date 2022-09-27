package com.springanything.basic;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class EqualsObjectTest {

	@DisplayName("List Equals Test")
	@Test
	void equals() {
		// given
		EqualsObject e1 = new EqualsObject("1", List.of("1", "2"));
		EqualsObject e2 = new EqualsObject("1", List.of("1", "2"));

		// when, then
		assertThat(e1).isEqualTo(e2);
	}

	@DisplayName("List hashCode Test")
	@Test
	void hashcode() {
		// given
		EqualsObject e1 = new EqualsObject("1", List.of("1", "2"));
		EqualsObject e2 = new EqualsObject("1", List.of("1", "2"));

		// 메모리 위치, hashCode method nono
		log.info("e1 = {}", System.identityHashCode(e1));
		log.info("e2 = {}", System.identityHashCode(e2));

		// when, then
		assertThat(e1.hashCode()).isEqualTo(e2.hashCode());
	}
}