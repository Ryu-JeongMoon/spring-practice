package com.springanything.mapping;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OuterRequest {

  private String name;
  private OuterEnum outerEnum;
  private int age;
  private List<String> list = new ArrayList<>();

  public OuterRequest(InnerRequest innerRequest) {
    this.name = innerRequest.getName();
    this.age = innerRequest.getAge();
  }
}

/*
외부 객체에서 내부 객체로 매핑하려면
1. 내부 객체는 불변 형태로 사용해도 되나 외부 객체에서 public-setter 열어야 함
2. 외부 객체에서 내부 객체의 데이터로 그냥 바인딩 꽂아버림 (생성자에 name, age, list 형태로 받기)
 */
