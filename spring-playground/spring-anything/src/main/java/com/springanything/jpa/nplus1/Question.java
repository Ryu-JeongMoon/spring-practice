package com.springanything.jpa.nplus1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Question {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(length = 50, nullable = false)
  @EqualsAndHashCode.Include
  private String title;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @ToString.Exclude
  private User user;

  public Question(String title, User user) {
    this.title = title;
    this.user = user;
  }
}
