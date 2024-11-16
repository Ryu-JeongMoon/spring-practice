package com.springanything.reactive;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebClientHelper {

  private final WebClient webClient;

  public <T> Mono<T> getMono(
    String uri,
    Class<T> clazz
  ) {
    return webClient.get()
      .uri(uri)
      .retrieve()
      .bodyToMono(clazz);
  }

  public <T> Flux<T> getFlux(
    String uri,
    Class<T> clazz
  ) {
    return webClient.get()
      .uri(uri)
      .retrieve()
      .bodyToFlux(clazz);
  }
}
