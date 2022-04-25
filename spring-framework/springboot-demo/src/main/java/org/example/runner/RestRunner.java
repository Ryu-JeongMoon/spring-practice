package org.example.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestRunner implements ApplicationRunner {

  private final RestTemplateBuilder restTemplateBuilder;

  @Override
  public void run(ApplicationArguments args) {
    RestTemplate restTemplate = restTemplateBuilder.build();

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    String pandaResult = restTemplate.getForObject("http://localhost:8080/panda", String.class);
    log.info("rest-template pandaResult = {}", pandaResult);

    String bearResult = restTemplate.getForObject("http://localhost:8080/bear", String.class);
    log.info("rest-template bearResult = {}", bearResult);

    stopWatch.stop();
    log.info("rest-template execution time = {}", stopWatch.prettyPrint());
  }
}

/*
execution time = StopWatch '': running time = 3141290375 ns
 */