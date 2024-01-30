package com.springanything.learning;

import java.time.Instant;
import java.util.Arrays;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;

import com.github.f4b6a3.tsid.Tsid;
import com.github.f4b6a3.tsid.TsidCreator;
import com.github.f4b6a3.tsid.TsidFactory;

class TSIDTest {

  @Test
  void generate() {
    // given
    Tsid tsid = TsidCreator.getTsid();
    System.out.println("tsid = " + tsid);

    long longTsid = tsid.toLong();
    System.out.println("longTsid = " + longTsid);

    byte[] bytesTsid = tsid.toBytes();
    System.out.println("bytesTsid = " + Arrays.toString(bytesTsid));

    String stringTsid = tsid.toString();
    System.out.println("stringTsid = " + stringTsid);

    Tsid fast = Tsid.fast();
    System.out.println("fast = " + fast);
  }

  @Test
  void customEpoch() {
    // given
    Instant customEpoch = Instant.parse("2023-01-01T00:00:00Z");
    RandomGenerator generator = RandomGenerator.getDefault();
    TsidFactory factory = TsidFactory.builder()
      .withCustomEpoch(customEpoch)
      .withNode(0)
      .withNodeBits(0)
      .withRandomFunction(() -> generator.nextInt())
      .build();

    // use the factory
    Tsid tsid = factory.create();
    System.out.println("tsid = " + tsid);

    Tsid fast = Tsid.fast();
    System.out.println("fast = " + fast);
  }
}
