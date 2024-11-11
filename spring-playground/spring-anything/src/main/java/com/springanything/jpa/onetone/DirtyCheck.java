package com.springanything.jpa.onetone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.springanything.tsid.Tsid;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString
public class DirtyCheck {

  @Id
  @Tsid
  private String id;

  @Column
  private String name;

  @ManyToOne(optional = false)
  @JoinColumn(name = "master_id")
  private Master master;

  public DirtyCheck(String name) {
    this.name = name;
  }
}
