package com.springanything.learning;

import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HashMapTest {

  @Test
  void maxLength() {
    // given
    int size = 1 << 24; // 2^24

    Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {
      // new HashMap<>(size);
      HashMap<Object, Object> map = new HashMap<>(size);
      for (int i = 0; i < size; i++) {
        map.put(i, i);
      }
    });

    // when

    // then

  }
}
