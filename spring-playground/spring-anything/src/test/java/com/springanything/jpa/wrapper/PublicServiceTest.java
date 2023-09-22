package com.springanything.jpa.wrapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractIntegrationTest;

class PublicServiceTest extends AbstractIntegrationTest {

  @Autowired
  private PublicService publicService;

  @DisplayName("Hidden Bean - HiddenRepository 정상 작동 ~!")
  @Test
  void create() {
    // given
    UUID saved = publicService.create();

    // when
    assertThat(saved).isNotNull();
  }
}
