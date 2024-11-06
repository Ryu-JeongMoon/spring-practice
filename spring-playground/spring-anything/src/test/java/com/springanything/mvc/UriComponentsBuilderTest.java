package com.springanything.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponentsBuilder;

class UriComponentsBuilderTest {

  private static final String URL = "http://localhost:8080";
  private static final String PATH = "/api/v1/users";

  @Test
  void fromHttpUrl() {
    // when
    String uriString = UriComponentsBuilder.fromHttpUrl(URL)
      .path(PATH)
      .queryParam("name", "John")
      .queryParam("age", 30)
      .build()
      .toUriString();

    // then
    assertThat(uriString).isEqualTo(URL + PATH + "?name=John&age=30");
  }

  @Test
  void fromOriginHeader() {
    // when
    String uriString = UriComponentsBuilder.fromUri(URI.create(URL))
      .path(PATH)
      .queryParam("name", "John")
      .queryParam("age", 30)
      .build()
      .toUriString();

    String uriStringWithOrigin = UriComponentsBuilder.fromOriginHeader("http://localhost:8080")
      .path(PATH)
      .queryParam("name", "John")
      .queryParam("age", 30)
      .build()
      .toUriString();

    // then
    String expected = URL + PATH + "?name=John&age=30";

    assertThat(uriString).isEqualTo(expected);
    assertThat(uriStringWithOrigin).isEqualTo(expected);
    assertThat(uriString).isEqualTo(uriStringWithOrigin);
  }
}
