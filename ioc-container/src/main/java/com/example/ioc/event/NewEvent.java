package com.example.ioc.event;

import lombok.Getter;

import java.util.Objects;

@Getter
public class NewEvent {

  private final int data;

  private final Object source;

  public NewEvent(Object source, int data) {
    this.source = source;
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NewEvent newEvent = (NewEvent) o;
    return getData() == newEvent.getData() && Objects.equals(getSource(), newEvent.getSource());
  }

  // Proxy, Reflection 사용하는 경우 필드에 직접 접근하는 것이 위험할 수 있으니
  // getter 이용하는 방식으로 만들어야 좋음
  @Override
  public int hashCode() {
    return Objects.hash(getData(), getSource());
  }
}
