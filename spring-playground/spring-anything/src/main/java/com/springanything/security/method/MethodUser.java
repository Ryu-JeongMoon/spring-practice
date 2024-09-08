package com.springanything.security.method;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.springanything.tsid.Tsid;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
public class MethodUser {

  @Id
  @Tsid
  private String id;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.VARCHAR)
  @Column(name = "role", nullable = false)
  private Role role;

  public MethodUser(String name, Role role) {
    this.name = name;
    this.role = role;
  }

  public String getAuthorities() {
    return role.name();
  }
}
