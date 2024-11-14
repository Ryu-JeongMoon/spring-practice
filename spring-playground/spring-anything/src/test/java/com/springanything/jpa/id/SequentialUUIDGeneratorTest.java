package com.springanything.jpa.id;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;

class SequentialUUIDGeneratorTest {

  @Test
  void generate() {
    // given
    TimeBasedEpochGenerator timeBasedEpochGenerator = Generators.timeBasedEpochGenerator();

    // when
    String id = timeBasedEpochGenerator.generate().toString();
    String uuid = UUID.randomUUID().toString();

    // then
    assertThat(id).hasSameSizeAs(uuid);
  }
}
