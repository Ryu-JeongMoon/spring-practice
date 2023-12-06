package com.springanything.jpa;

import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import com.springanything.AbstractRepositoryTest;

class PandaRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private PandaRepository pandaRepository;

  @Test
  void saveByVirtualThread() {
    // given
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    // when
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 0; i < 1000; i++) {
        executor.submit(() -> {
          log.info("panda = {}", pandaRepository.save(new Panda()));
        });
      }

      // then
      stopWatch.stop();
      log.info("elapsed = {}", stopWatch.prettyPrint());
    }
  }
}
