package com.springanything.learning;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import com.github.f4b6a3.tsid.TsidFactory;

class UUID7Test {

  private static final TimeBasedEpochGenerator GENERATOR = Generators.timeBasedEpochGenerator();
  private static final CustomTsidCreator CUSTOM_TSID_CREATOR = new CustomTsidCreator();
  private static final TsidFactory TSID_FACTORY = TsidFactory.builder()
    .withCustomEpoch(Instant.parse("2023-01-01T00:00:00Z"))
    .withNode(0)
    .withNodeBits(0)
    .withRandomFunction(() -> RandomGenerator.getDefault().nextInt())
    .build();

  @DisplayName("UUID v7 unique id generation")
  @Test
  void generate() throws InterruptedException {
    for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
      generateAsynchronously();
    }

    Thread.sleep(1000);
  }

  private void generateAsynchronously() {
    StringBuilder sb = new StringBuilder();
    CompletableFuture.runAsync(() -> {
      for (int i = 0; i < 10_000; i++) {
        // UUID uuid = GENERATOR.generate();
        // UUID uuid = UuidCreator.getTimeOrderedEpoch();
        // Tsid uuid = TSID_FACTORY.create();
        String uuid = CUSTOM_TSID_CREATOR.create();
        sb.append(uuid).append("\n");
      }
      System.out.println(sb.deleteCharAt(sb.length() - 1));
    });
  }
}

/*
Preference > Editor > General > Console
Override console cycle buffer size -> 3072 KB
 */