package org.junit;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DisplayNameGenerationTest {

  @Test
  void display_name_generated() {
    System.out.println("DisplayNameGenerationTest.display_name_generated");
  }
}

/*
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) 설정해
메서드 이름의 언더 스코어를 스페이스로 바꿔준다
근디 얘보다는 @DisplayName 쓰는게 나을 듯?!
 */