package com.springanything.jpa.enums;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "white_bear")
@NoArgsConstructor
@Getter
public class WhiteBear {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated
  @Column(name = "bear_option")
  private BearOption bearOption;

  public WhiteBear(BearOption bearOption) {
    this.bearOption = bearOption;
  }
}
