package org.example.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebClientRunner implements ApplicationRunner {

  private final WebClient.Builder builder;


  @Override
  public void run(ApplicationArguments args) {
    WebClient webClient = builder.build();

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    webClient.get().uri("http://localhost:8080/panda")
      .retrieve()
      .bodyToMono(String.class)
      .subscribe(s -> {
        log.info("web-client result = {}", s);

        if (stopWatch.isRunning()) {
          stopWatch.stop();
          log.info("web-client execution time = {}", stopWatch.prettyPrint());
          stopWatch.start();
        }
      });

    webClient.get().uri("http://localhost:8080/bear")
      .retrieve()
      .bodyToMono(String.class)
      .subscribe(s -> {
        log.info("web-client result = {}", s);

        if (stopWatch.isRunning()) {
          stopWatch.stop();
          log.info("web-client execution time = {}", stopWatch.prettyPrint());
        }
      });
  }
}

/*
execution time = StopWatch '': running time = 1362336475 ns
execution time = StopWatch '': running time = 2302232178 ns

그럼 요놈을 사용하는 이유는 블락킹 당하지 않고 유연하게 조합 가능하기 때문?!
 */