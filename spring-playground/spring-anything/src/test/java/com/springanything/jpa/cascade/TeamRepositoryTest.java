package com.springanything.jpa.cascade;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractRepositoryTest;

class TeamRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private TeamRepository teamRepository;

  private Long id;

  @BeforeEach
  void setUp() {
    SubFlag subFlag0 = SubFlag.builder()
      .name("subFlag0")
      .build();

    SubFlag subFlag1 = SubFlag.builder()
      .name("subFlag1")
      .build();

    Flag flag0 = Flag.builder()
      .name("flag0")
      .subFlags(Set.of(subFlag0, subFlag1))
      .build();

    Flag flag1 = Flag.builder()
      .name("flag1")
      .build();

    SubFlag subFlag2 = SubFlag.builder()
      .name("subFlag2")
      .build();

    Flag flag2 = Flag.builder()
      .name("flag2")
      .subFlags(Set.of(subFlag2))
      .build();

    SubFlag subFlag3 = SubFlag.builder()
      .name("subFlag3")
      .build();

    Flag flag3 = Flag.builder()
      .name("flag3")
      .subFlags(Set.of(subFlag3))
      .build();

    Team team = new Team();
    team.setFlags(List.of(flag0, flag1, flag2, flag3));
    id = teamRepository.save(team).getId();
  }

  @DisplayName("@OnDelete(action = OnDeleteAction.CASCADE) - DBMS 관리")
  @Test
  void deleteOnCascade() {
    // given
    deleteTeam(id);
    flushAndClear();

    // then
    List<Team> teams = teamRepository.findAll();

    log.info("teams: {}", teams);
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
