package com.springanything.http.etag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import com.springanything.tsid.Tsid;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class EtagEntity {

  @Id
  @Tsid
  private String id;

  @Column
  private String name;

  @Column
  private String description;

  public EtagEntity(String name, String description) {
    this.name = name;
    this.description = description;
  }
}
