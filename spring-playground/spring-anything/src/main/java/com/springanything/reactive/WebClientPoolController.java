package com.springanything.reactive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springanything.util.WebClientHelper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WebClientPoolController {

  private static final String FAKE_REST_API_URI = "https://fakerestapi.azurewebsites.net/api/v1/Activities";
  private static final String REQ_RES_REST_API_URI = "https://reqres.in/api/users?page=2";

  private final WebClientHelper webClientHelper;

  @GetMapping("/test/fake-rest-api")
  public Flux<Activity> getActivities() {
    return webClientHelper.getFlux(FAKE_REST_API_URI, Activity.class);
  }

  @GetMapping("/test/req-res-rest-api")
  public Mono<String> getDummy() {
    return webClientHelper.getMono(REQ_RES_REST_API_URI, String.class);
  }

  public record Activity(int id, String title, String dueDate, boolean completed) {

  }
}
