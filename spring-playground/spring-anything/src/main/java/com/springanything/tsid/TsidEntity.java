package com.springanything.tsid;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TsidEntity {

  @Id
  @GenericGenerator(name = "tsid", type = TimeSortedIdGenerator.class)
  @GeneratedValue(generator = "tsid")
  public String id;
}
