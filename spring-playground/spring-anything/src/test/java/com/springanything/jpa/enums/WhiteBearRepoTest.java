package com.springanything.jpa.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractRepositoryTest;

class WhiteBearRepoTest extends AbstractRepositoryTest {

	@Autowired
	private WhiteBearRepo whiteBearRepo;

	@DisplayName("Enum, Ordinal 저장 시 index 0부터 시작")
	@Test
	void save() {
		// given
		WhiteBear whiteBear = new WhiteBear(BearOption.ASIA);

		// when
		whiteBearRepo.save(whiteBear);

		// then
		flushAndClear();
	}
}