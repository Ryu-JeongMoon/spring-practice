package com.springanything.jpa.relationship;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Agent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "option_id", updatable = false, nullable = false)
  @ToString.Exclude
  private AgentOption option;

  @Builder(access = AccessLevel.PRIVATE)
  private Agent(Long id, AgentOption option) {
    this.id = id;
    this.option = option;
  }

  public static Agent from(AgentOption option) {
    return Agent.builder()
      .option(option)
      .build();
  }
}
