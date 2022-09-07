package com.springanything.junit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class PandaBear {

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  private String name;

  @Builder
  public PandaBear(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
