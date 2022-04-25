package org.example.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.AsyncRestTemplate;

@Slf4j
@Component
public class AsyncRestRunner implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) {
    AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    asyncRestTemplate.getForEntity("http://localhost:8080/panda", String.class)
      .addCallback(
        result -> {
          log.info("async-template result = {}", result);

          if (stopWatch.isRunning()) {
            stopWatch.stop();
            log.info("async-template execution time = {}", stopWatch.prettyPrint());
            stopWatch.start();
          }
        },
        error -> log.info("async-template error = {}", error)
      );

    asyncRestTemplate.getForEntity("http://localhost:8080/bear", String.class)
      .addCallback(
        result -> {
          log.info("async-template result = {}", result);

          if (stopWatch.isRunning()) {
            stopWatch.stop();
            log.info("async-template execution time = {}", stopWatch.prettyPrint());
          }
        },
        error -> log.info("async-template error = {}", error)
      );
  }
}

/*
execution time = StopWatch '': running time = 1112779798 ns
execution time = StopWatch '': running time = 2088192992 ns

async-rest-template [deprecated] ..!
오잉 async 가 web-client 보다 빠르다?!
 */