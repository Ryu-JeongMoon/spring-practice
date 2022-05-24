package com.springservletthymeleaf.sample;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
public class Yahoo {

  private String name;
  private int age;

  protected Yahoo() {
  }

  public Yahoo(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
