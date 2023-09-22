package com.springanything.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecursiveObjectTest {

  @DisplayName("")
  @Test
  void delete() {
    // given
    RecursiveObject r8 = new RecursiveObject(4, "8", null);
    RecursiveObject r7 = new RecursiveObject(4, "7", r8);
    RecursiveObject r6 = new RecursiveObject(4, "6", r7);
    RecursiveObject r5 = new RecursiveObject(4, "5", r6);
    RecursiveObject r4 = new RecursiveObject(3, "4", r5);
    RecursiveObject r3 = new RecursiveObject(3, "3", r4);
    RecursiveObject r2 = new RecursiveObject(2, "2", r3);
    RecursiveObject r1 = new RecursiveObject(1, "1", r2);

    // when
    // RecursiveObject result = r1.deleteRecursively(r1, "4");

    // then

  }
}
