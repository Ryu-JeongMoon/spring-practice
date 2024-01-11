package com.springanything.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
  "springanything.conditional=TRUE"
})
class ConditionalComponentTest {

  @Autowired
  private ConditionalComponent conditionalComponent;

  /**
   * isMatch() method of OnPropertyCondition compares values case-insensitively.
   *
   * @see org.springframework.boot.autoconfigure.condition.OnPropertyCondition#getMatchOutcome
   */
  @DisplayName("attribute 'havingValue' of @ConditionalOnProperty is case-insensitive")
  @Test
  void injected() {
    assertThat(conditionalComponent).isNotNull();
  }
}
