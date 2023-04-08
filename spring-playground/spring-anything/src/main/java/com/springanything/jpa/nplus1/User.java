package com.springanything.jpa.nplus1;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 10, nullable = false)
  private String name;

  @BatchSize(size = 100)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  // @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private Set<Article> articles = Collections.emptySet();

  public User(String name) {
    this.name = name;
  }

  @Builder
  public User(Long id, String name, Set<Article> articles) {
    this.id = id;
    this.name = name;
    this.articles = articles;
  }
}
