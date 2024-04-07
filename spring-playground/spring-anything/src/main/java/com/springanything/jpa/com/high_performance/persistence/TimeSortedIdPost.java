package com.springanything.jpa.com.high_performance.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.springanything.tsid.TimeSortedIdGenerator;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "time_sorted_id_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TimeSortedIdPost {

  @Id
  @GeneratedValue(generator = "tsid")
  @GenericGenerator(name = "tsid", type = TimeSortedIdGenerator.class)
  @EqualsAndHashCode.Include
  private String id;

  @Setter
  private String title;
}
