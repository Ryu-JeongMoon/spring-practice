package com.springanything.basic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.ToString;

class CallByValueTest {

  @Test
  void test() {
    // given
    // a = 1234 (heap memory address)
    // b = 5678 (heap memory address)
    TestableObject a = new TestableObject(1);
    TestableObject b = new TestableObject(2);

    // when
    // 기존 객체의 참조값을 복사하여 다른 메서드로 넘김
    // modify(a, b) -> modify(1234, 5678)
    modify(a, b);

    // then
    assertThat(a.value).isEqualTo(111);
    assertThat(b.value).isEqualTo(2);
  }

  // a는 new TestableObject(1)의 주소값을 가지고 있고 modify() 메서드의 인자로 넘어갈 때 a의 주소값이 복사 됨
  private void modify(TestableObject a, TestableObject b) {
    // a.value = 111 -> 1234.value = 111
    // a의 주소값으로 찾아가서 value를 111로 변경
    a.value = 111;

    // b = a -> b = 1234
    // b의 주소값을 a의 주소값으로 변경
    // 여기서 참조값을 변경한다고 해서 main scope b의 참조값이 변경되는 것이 아님
    // b는 new TestableObject(2)의 주소값을 가지고 있던 변수일 뿐
    b = a;
  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @ToString
  private static class TestableObject {

    private int value;
  }
}

/*
TestableObject a = new TestableObject(1);
a를 받아들일 때 객체 자체로 받아들여선 안 됨, 객체의 주소값으로 받아들여야 함
다른 메서드로 제어권이 넘어가 scope 달라질 때 객체 참조값이 달라진다고 기존 객체의 참조값이 변경되지 않음
기존 객체가 가지고 있는 필드 값을 변경할 땐 기존 객체의 참조값도 영향을 받음
*/
