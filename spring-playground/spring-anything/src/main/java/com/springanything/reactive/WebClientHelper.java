package com.springanything.reactive;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class WebClientHelper {

  public <T> Flux<T> getFlux(String uri, Class<T> clazz) {
    return null;
  }

  public <T> Mono<T> getMono(String uri, Class<T> clazz) {
    return null;
  }
}
