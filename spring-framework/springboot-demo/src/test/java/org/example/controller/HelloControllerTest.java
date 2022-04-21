package org.example.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import org.example.Panda;
import org.example.service.HelloService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {

  @Autowired
  WebTestClient webTestClient;

  @MockBean
  HelloService mockHelloService;

  @Test
  @DisplayName("WebTestClient 비동기로 동작 & 깔꼼")
  void hello() {
    Mockito.when(mockHelloService.getDeclaration()).thenReturn("hello springboot !");

    webTestClient.get().uri("/hello").exchange()
      .expectStatus().isOk()
      .expectBody(String.class).isEqualTo("hello springboot !");
  }

  @Test
  @DisplayName("JSON 팬더 반환")
  void checkPanda_JSON() {
    Panda panda = new Panda("불곰", 15);
    Mockito.when(mockHelloService.getPanda()).thenReturn(panda);

    webTestClient.post().uri("/panda/create")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .body(BodyInserters.fromValue(new Panda("불곰", 15)))
      .exchange()
      .expectStatus().isCreated()
      .expectBody(Panda.class).value(is(equalTo(panda)));
  }

  @Test
  @DisplayName("XML 팬더 반환")
  void checkPanda_XML() throws Exception {
    Panda panda = new Panda("불곰", 15);
    Mockito.when(mockHelloService.getPanda()).thenReturn(panda);

    FluxExchangeResult<Panda> result = webTestClient.post().uri("/panda/create")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_XML)
      .body(BodyInserters.fromValue(new Panda("불곰", 15)))
      .exchange()
      .expectStatus().isCreated()
      .returnResult(Panda.class);

    MockMvcWebTestClient.resultActionsFor(result)
      .andExpect(xpath("/Panda/age").number(Double.valueOf(15)))
      .andExpect(xpath("/Panda/name").string("불곰"));
  }

  @Test
  @DisplayName("HATEOAS 팬더 반환")
  void getHateoasPanda() {
    webTestClient.get().uri("/panda").exchange()
      .expectBody()
      .jsonPath("$._links").exists()
      .jsonPath("$.name").exists();
  }
}

/*
jsonPath  -> $.<변수명>
xpath     -> /<객체>/<변수명>

크흠 숫자는 Double.valueOf()로 감싸야 한다?!
 */