package com.springanything.jpa.cascade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sub_flag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SubFlag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Builder
  public SubFlag(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
