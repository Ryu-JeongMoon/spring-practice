package com.springanything.learning;

import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import de.mkammerer.snowflakeid.SnowflakeIdGenerator;
import de.mkammerer.snowflakeid.options.Options;
import de.mkammerer.snowflakeid.structure.Structure;
import de.mkammerer.snowflakeid.time.MonotonicTimeSource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class SnowflakeIdTest {

  /**
   * <pre>
   * Unique ID 생성 시, Shard 또는 Node ID가 영향을 미치므로 샤딩 시 유용
   * 여러 인스턴스 간 전체 시간 순 정렬이 되지 않음 + 중복 ID 발생</pre>
   */
  @DisplayName("Snowflake-like ID 생성")
  @Test
  void test() throws InterruptedException {
    // then
    for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
      generateAsynchronously();
    }

    Thread.sleep(1000);
  }

  private void generateAsynchronously() {
    // given
    int generatorId = 0;

    // when
    Structure structure = new Structure(49, 1, 13);
    SnowflakeIdGenerator generator
      = SnowflakeIdGenerator.createCustom(generatorId, MonotonicTimeSource.createDefault(), structure, Options.createDefault());

    StringBuilder sb = new StringBuilder();
    CompletableFuture.runAsync(() -> {
      for (int i = 0; i < 100; i++) {
        long uuid = generator.next();
        sb.append(uuid).append("\n");
      }
      System.out.println(sb.deleteCharAt(sb.length() - 1));
    });
  }
}
