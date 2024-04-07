package com.springanything.jpa.cascade;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Audited
@Getter
public class Flag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
  private Set<SubFlag> subFlags = new HashSet<>();

  @Builder
  public Flag(Long id, String name, Team team, Set<SubFlag> subFlags) {
    this.id = id;
    this.name = name;
    this.team = team;
    this.subFlags = subFlags;
  }
}
