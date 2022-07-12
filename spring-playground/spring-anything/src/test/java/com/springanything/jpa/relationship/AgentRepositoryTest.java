package com.springanything.jpa.relationship;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.springanything.config.TestConfig;

@DataJpaTest
@Import(TestConfig.class)
class AgentRepositoryTest {

	@Autowired
	EntityManager em;
	@Autowired
	AgentRepository agentRepository;
	@Autowired
	AgentOptionRepository agentOptionRepository;

	@Test
	@DisplayName("ID만 넣고 영속화해도 오께이")
	void save() {
		// given, AgentOption을 영속화한 상태
		AgentOption option = AgentOption.builder()
			.id(1L)
			.name("panda")
			.build();
		agentOptionRepository.save(option);

		em.flush();
		em.clear();

		// 임시로 id만 동일하게 만든 AgentOption 생성 (요놈은 영속화 되지 않은 상태)
		AgentOption simpleOption = AgentOption.builder()
			.id(1L)
			.build();

		// 영속화 되지 않은 놈으로 Agent 생성 후 영속화
		Agent agent = Agent.from(simpleOption);
		Long agentId = agentRepository.save(agent).getId();

		// when
		em.flush();
		em.clear();

		// then, equals 오께이
		AgentOption findOption = agentRepository.findById(agentId)
			.orElseThrow()
			.getOption();

		assertThat(findOption).isEqualTo(option);
	}
}