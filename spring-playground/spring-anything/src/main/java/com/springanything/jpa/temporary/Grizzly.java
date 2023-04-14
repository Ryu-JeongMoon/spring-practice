package com.springanything.jpa.temporary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Grizzly {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @Transient
  private boolean nameChanged;

  public void setName(String name) {
    if (StringUtils.hasText(this.name)) {
      this.nameChanged = true;
    }

    this.name = name;
  }
}
