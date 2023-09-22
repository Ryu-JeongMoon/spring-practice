package com.springanything.jpa.cascade;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Audited
@Getter
@Setter
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  // cascade=ALL is equivalent to cascade={PERSIST, MERGE, REMOVE, REFRESH, DETACH}.
  @OneToMany(
    mappedBy = "team",
    fetch = FetchType.LAZY,
    cascade = { CascadeType.PERSIST, CascadeType.MERGE },
    orphanRemoval = true
  )
  private List<Member> members = new ArrayList<>();

  @OneToMany(
    mappedBy = "team",
    cascade = CascadeType.ALL,
    // cascade = { CascadeType.PERSIST, CascadeType.MERGE },
    orphanRemoval = true
  )
  // @OnDelete(action = OnDeleteAction.CASCADE)
  private List<Flag> flags = new ArrayList<>();

  public Team(String name) {
    this.name = name;
  }

  public Team(String name, List<Flag> flags) {
    this.name = name;
    this.flags = flags;
  }

  public void addMember(Member member) {
    members.add(member);
    member.setTeam(this);
  }
}

/*
@OneToMany(mappedBy = "entity")
mappedBy는 상관 없고 @OnDelete 때문에 DB 삭제해버리니 애플리케이션에서는 쿼리 확인할 수가 없다
 */
