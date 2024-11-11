package com.springanything.jpa.custom_id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "custom_bamboo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomBamboo {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", type = SequentialUUIDGenerator.class)
  private String id;

  @Column
  private String name;
}
