package com.example.ioc.binding;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BindingObject {

  private Long id;

  private String name;

  public BindingObject(Long id) {
    this.id = id;
  }
}
