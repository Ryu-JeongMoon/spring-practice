package com.springanything.jpa.cascade;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractRepositoryTest;

class MemberRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private TeamRepository teamRepository;

  @DisplayName("CascadeType.REMOVE - Member의 생명주기를 관리하는 Team을 삭제함으로써 Member도 삭제된다")
  @Test
  void cascadeType_Remove_InCaseOfTeamRemoval() {
    // given
    Member member1 = new Member();
    Member member2 = new Member();

    Team team = new Team();

    team.addMember(member1);
    team.addMember(member2);

    teamRepository.save(team);

    // when
    teamRepository.delete(team);

    // then
    List<Team> teams = teamRepository.findAll();
    List<Member> members = memberRepository.findAll();

    assertThat(teams).hasSize(0);
    assertThat(members).hasSize(0);
  }

  // CascadeType.REMOVE 옵션은 부모와 자식의 관계가 끊어졌다 해서 자식을 삭제하지 않는다.
  @DisplayName("CascadeType.REMOVE - 부모 엔티티(Team)에서 자식 엔티티(Member)를 제거하는 경우")
  @Test
  void cascadeType_Remove_InCaseOfMemberRemovalFromTeam() {
    // given
    Member member1 = new Member();
    Member member2 = new Member();

    Team team = new Team();

    team.addMember(member1);
    team.addMember(member2);

    teamRepository.save(team);

    // when
    team.getMembers().remove(0);

    // then
    List<Team> teams = teamRepository.findAll();
    List<Member> members = memberRepository.findAll();

    assertThat(teams).hasSize(1);
    assertThat(members).hasSize(1);
  }

  @DisplayName("orphanRemoval = true - 부모 엔티티(Team)을 삭제하는 경우")
  @Test
  void orphanRemoval_True_InCaseOfTeamRemoval() {
    // given
    Member member1 = new Member();
    Member member2 = new Member();

    Team team = new Team();

    team.addMember(member1);
    team.addMember(member2);

    teamRepository.save(team);

    // when
    teamRepository.delete(team);

    // then
    List<Team> teams = teamRepository.findAll();
    List<Member> members = memberRepository.findAll();

    assertThat(teams).hasSize(0);
    assertThat(members).hasSize(0);
  }

  @DisplayName("orphanRemoval = true - 부모 엔티티(Team)에서 자식 엔티티(Member)를 제거하는 경우")
  @Test
  void orphanRemoval_True_InCaseOfMemberRemovalFromTeam() {
    // given
    Member member1 = new Member();
    Member member2 = new Member();

    Team team = new Team();

    team.addMember(member1);
    team.addMember(member2);

    teamRepository.save(team);

    // when
    team.getMembers().remove(0);

    // then
    List<Team> teams = teamRepository.findAll();
    List<Member> members = memberRepository.findAll();

    assertThat(teams).hasSize(1);
    assertThat(members).hasSize(1);
  }
}

/*
CascadeType.PERSIST + CascadeType.REMOVE
Cascade를 건 쪽에서 생명주기를 관리하게 된다
Team -> List<Member> 이므로 Team이 Member의 생명주기를 관리한다
그렇다고 해서 List<Member>에서 요소를 제거하더라도 제거된 Member가 삭제되지는 않는다
단순히 연관관계가 끊어진 것 뿐임둥

orphanRemoval=true
생명주기를 관리하는 쪽에서 자식을 제거하면 연관관계가 끊어지므로 자식을 삭제한다

Cascade로 영속성 전이를 하든 orphanRemoval로 고아 객체를 삭제하든
생명 주기를 관리하는 객체를 삭제하면 자식 객체들이 삭제되는 것은 동일하다
둘의 차이는 부모에서 자식과의 연관 관계를 끊을 때 자식 객체를 어떻게 처리할 것인지에 달렸다
Cascade -> 일단 냅둠
orphanRemoval -> 고아 객체 삭제
 */
