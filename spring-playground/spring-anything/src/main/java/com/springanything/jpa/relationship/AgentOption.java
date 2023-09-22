package com.springanything.jpa.relationship;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "agent_option",
  indexes = @Index(name = "name_idx", columnList = "name")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@ToString
public class AgentOption {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ColumnDefault("0")
  private Integer age;

  @Builder
  private AgentOption(Long id, String name, Integer age) {
    this.id = id;
    this.name = name;
    this.age = age;
  }

  public void change(String name, Integer age) {
    this.name = name;
    this.age = age;
  }
}
