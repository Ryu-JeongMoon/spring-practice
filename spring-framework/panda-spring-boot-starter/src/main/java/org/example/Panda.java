package org.example;

public class Panda {

  String name;

  int age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Panda() {
  }

  public Panda(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override
  public String toString() {
    return "Panda{" +
      "name='" + name + '\'' +
      ", age=" + age +
      '}';
  }
}
