package com.springservlet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class MemberRepositoryTest {

  MemberRepository memberRepository = MemberRepository.getInstance();

  @AfterEach
  void tearDown() {
    memberRepository.clear();
  }

  @Test
  @DisplayName("")
  void save() {
    // given
    Member panda = Member.builder()
      .username("panda")
      .age(955)
      .build();
    Member savedMember = memberRepository.save(panda);

    // when
    Member result = memberRepository.findById(savedMember.getId());

    log.info("result = {}", result);

    // then
    assertAll(
      () -> assertThat(result).isNotNull(),
      () -> assertThat(result.getId()).isEqualTo(savedMember.getId())
    );
  }

  @Test
  @DisplayName("find all")
  void findAll() {
    // given
    for (int i = 0; i < 5; i++) {
      Member panda = Member.builder()
        .username("panda => " + i)
        .age(i + 500)
        .build();
      memberRepository.save(panda);
    }

    // when
    List<Member> members = memberRepository.findAll();
    members.forEach(member -> log.info("member = {}", member));

    // then
    assertAll(
      () -> assertThat(members).isNotEmpty(),
      () -> assertThat(members.size()).isEqualTo(5)
    );
  }
}