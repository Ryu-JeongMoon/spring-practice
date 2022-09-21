package com.springanything.jpa.cascade;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;

import com.springanything.config.TestConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeamRepositoryTest {

	@Autowired
	private TeamRepository teamRepository;

	@DisplayName("@OnDelete(action = OnDeleteAction.CASCADE) - DBMS 관리")
	@Commit
	@Test
	void deleteOnCascade() {
		// given
		// Long id = insertTeam();
		// log.info("id: {}", id);

		// when
		deleteTeam(5L);

		// then
		List<Team> teams = teamRepository.findAll();

		log.info("teams: {}", teams);
	}

	private Long insertTeam() {
		Flag flag0 = Flag.builder()
			.name("flag0")
			.build();

		Flag flag1 = Flag.builder()
			.name("flag1")
			.build();

		Flag flag2 = Flag.builder()
			.name("flag2")
			.build();

		Flag flag3 = Flag.builder()
			.name("flag3")
			.build();

		Team team = new Team();
		team.setFlags(List.of(flag0, flag1, flag2, flag3));
		return teamRepository.save(team).getId();
	}

	private void deleteTeam(Long id) {
		Team team = teamRepository.findById(id).get();
		List<Flag> flags = team.getFlags();
		log.info("flags: {}", flags);

		teamRepository.delete(team);
	}
}

/*
@OnDelete(action = OnDeleteAction.CASCADE) - Hibernate Annotation
DB 자체에서 on delete cascade 조건 걸림둥

alter table flag
	 add constraint FKe3hbsuh6inpf88oyigc4fhx62
	 foreign key (team_id)
	 references team
	 on delete cascade
 */