package com.springanything.jpa.nplus1;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

  // @BatchSize(size = 100)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<Article> articles = new HashSet<>();
  // private List<Article> articles;

  // @BatchSize(size = 100)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<Question> questions = new HashSet<>();
  // private List<Question> questions = new ArrayList<>();

  public User(String name) {
    this.name = name;
  }

  @Builder
  public User(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
