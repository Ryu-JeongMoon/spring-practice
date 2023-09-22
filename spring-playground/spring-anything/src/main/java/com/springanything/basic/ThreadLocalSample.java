package com.springanything.basic;

public class ThreadLocalSample {

  private static final ThreadLocal<String> value = new ThreadLocal<>();

  public static void main(String[] args) {
    value.set("Hello");
    System.out.println(value.get());

    value.remove();
    System.out.println(value.get());

    value.set("World");
    System.out.println(value.get());

    value.set("Hello");
    System.out.println(value.get());
  }
}
