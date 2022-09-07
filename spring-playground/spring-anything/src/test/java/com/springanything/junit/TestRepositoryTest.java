package com.springanything.junit;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.NestedTestConfiguration;

import com.springanything.config.TestConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@Import(value = TestConfig.class)
public class TestRepositoryTest {

	@Autowired
	EntityManager em;

	@Nested
	@NestedTestConfiguration(value = NestedTestConfiguration.EnclosingConfiguration.INHERIT)
	class NestedClassTest {

		/**
		 * NestedTestConfiguration.EnclosingConfiguration<br/>
		 * INHERIT 사용 시 outer class의 context를 상속 받는다<br/>
		 * OVERRIDE 사용 시 inner class만의 context를 만든다<br/>
		 * Default INHERIT 이기 때문에 SpringFramework 5.3 이후로 @Nested 사용 시 문제 없다
		 */
		@RepeatedTest(2)
		@DisplayName("@Nested 내부의 Rollback 여부 확인, name의 unique 제약 조건을 걸어 rollback 되지 않으면 예외가 발생한다")
		void nested() {
			// given
			PandaBear panda = PandaBear.builder()
				.name("panda-bear")
				.build();

			// when
			em.persist(panda);
			Long id = panda.getId();

			em.flush();
			em.clear();

			// then
			PandaBear result = em.find(PandaBear.class, id);
			log.info("result = {}", result);
		}
	}
}
