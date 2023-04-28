package com.springanything.learning;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;

class UUID7Test {

  private static final TimeBasedEpochGenerator GENERATOR = Generators.timeBasedEpochGenerator();

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
        UUID uuid = GENERATOR.generate();
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