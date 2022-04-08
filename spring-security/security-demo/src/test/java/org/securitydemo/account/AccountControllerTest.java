package org.securitydemo.account;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.securitydemo.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

@AutoConfigureMockMvc
@Import(SecurityConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

  @Autowired
  WebTestClient webTestClient;

  @MockBean
  AccountService accountService;

  @Test
  @WithAnonymousUser
  @DisplayName("루트 접근 오께이")
  void getRootPageByAnonymousUser() throws Exception {
    EntityExchangeResult<byte[]> exchangeResult = webTestClient.get()
      .uri("/")
      .exchange()
      .expectStatus().is2xxSuccessful()
      .expectBody().returnResult();

    MockMvcWebTestClient.resultActionsFor(exchangeResult)
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "panda", roles = "USER")
  @DisplayName("로그인된 회원 루트 접근 오께이")
  void getRootPageByAlreadyLoginUser() throws Exception {
    EntityExchangeResult<byte[]> exchangeResult = webTestClient.get()
      .uri("/")
      .exchange()
      .expectStatus().is2xxSuccessful()
      .expectBody().returnResult();

    MockMvcWebTestClient.resultActionsFor(exchangeResult)
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "panda", roles = "USER")
  @DisplayName("어드민 페이지 접근 노노")
  void getAdminPageByUser() throws Exception {
    EntityExchangeResult<byte[]> exchangeResult = webTestClient.get()
      .uri("/admin")
      .exchange()
      .expectStatus().is4xxClientError()
      .expectBody().returnResult();

    MockMvcWebTestClient.resultActionsFor(exchangeResult)
      .andDo(print())
      .andExpect(status().isForbidden());
  }
}