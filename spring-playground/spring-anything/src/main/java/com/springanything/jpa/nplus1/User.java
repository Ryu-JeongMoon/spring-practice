package com.springanything.jpa.nplus1;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

  // @BatchSize(size = 100)
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private Set<Article> articles = new HashSet<>();
  // private List<Article> articles = new ArrayList<>();

  @BatchSize(size = 100)
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private Set<Question> questions = new HashSet<>();
  // private List<Question> questions = new ArrayList<>();

  @Column(length = 10, nullable = false)
  private String name;

  public User(String name) {
    this.name = name;
  }

  @Builder
  public User(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
