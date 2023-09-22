package com.springanything.lock;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.springanything.util.CaseUtils;

class CustomScheduledAnnotationBeanPostProcessorTest {

  @Test
  void toCamelCase() {
    // given
    String batchName = "BlahBatch";

    // when
    String result = CaseUtils.toCamelCase(batchName);

    // then
    assertThat(result).isEqualTo("blahBatch");
  }
}
