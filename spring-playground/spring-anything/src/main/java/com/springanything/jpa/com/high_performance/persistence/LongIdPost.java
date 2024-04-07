package com.springanything.jpa.com.high_performance.persistence;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "long_id_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LongIdPost {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Setter
  private String title;
}
