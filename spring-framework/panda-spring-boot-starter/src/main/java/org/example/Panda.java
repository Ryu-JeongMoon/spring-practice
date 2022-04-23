package org.example;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Panda panda = (Panda) o;
    return getAge() == panda.getAge() && Objects.equals(getName(), panda.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getAge());
  }
}
