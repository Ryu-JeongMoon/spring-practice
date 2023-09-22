package com.springanything.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.springanything.AbstractIntegrationTest;
import com.springanything.mapping.TestRequest;

class WebClientConfigTest extends AbstractIntegrationTest {

  @Autowired
  private WebClient webClient;

  @Test
  @DisplayName("setter 없이 WebClient Response 매핑도 가능하다")
  void webClientResponseMapping() {
    // given
    TestRequest vo = TestRequest.of("test-name", 100, new TestRequest.TestInnerRequest("test-inner-name"));
    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("name", vo.getName());
    formData.add("age", String.valueOf(vo.getAge()));
    formData.add("testInnerRequest", vo.getTestInnerRequest().getInnerName());

    // when
    TestRequest result = webClient.post()
      .uri("http://localhost:8080/test/model-attribute")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .bodyValue(formData)
      .retrieve()
      .bodyToFlux(TestRequest.class)
      .toStream()
      .findAny()
      .orElseGet(TestRequest::empty);

    log.info("result = {}", result);

    // then
    assertAll(
      () -> assertThat(result.getName()).isEqualTo(vo.getName()),
      () -> assertThat(result.getAge()).isEqualTo(vo.getAge()),
      () -> assertThat(result.getTestInnerRequest()).isEqualTo(vo.getTestInnerRequest())
    );
  }
}
