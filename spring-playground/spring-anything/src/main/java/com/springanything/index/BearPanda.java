package com.springanything.index;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(
  name = "bear_panda",
  indexes = {
  @Index(name = "bear_panda_name_idx", columnList = "name1", unique = true),
  @Index(name = "bear_panda_names_idx", columnList = "name2, name3", unique = true)
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class BearPanda {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 767)
  private String name1;

  @Column(length = 382)
  private String name2;

  @Column(length = 382)
  private String name3;

  @Builder
  public BearPanda(String name1, String name2, String name3) {
    this.name1 = name1;
    this.name2 = name2;
    this.name3 = name3;
  }
}
