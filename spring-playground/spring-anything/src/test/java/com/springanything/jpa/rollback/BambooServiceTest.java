package com.springanything.jpa.rollback;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractIntegrationTest;
import com.springanything.jpa.Bamboo;
import com.springanything.jpa.BambooRepository;

class BambooServiceTest extends AbstractIntegrationTest {

  @Autowired
  private BambooRepository bambooRepository;
  @Autowired
  private BambooService bambooService;
  @Autowired
  private EntityManager entityManager;

  @BeforeEach
  void setUp() {
    Bamboo a = Bamboo.builder()
      .id(1L)
      .type("a")
      .build();
    Bamboo b = Bamboo.builder()
      .id(3L)
      .type("b")
      .build();
    Bamboo c = Bamboo.builder()
      .id(5L)
      .type("c")
      .build();
    Bamboo d = Bamboo.builder()
      .id(7L)
      .type("d")
      .build();
    Bamboo e = Bamboo.builder()
      .id(9L)
      .type("e")
      .build();
    bambooRepository.saveAll(List.of(a, b, c, d, e));
  }

  @DisplayName("compensating transaction")
  @Test
  void deleteById() {
    // given
    bambooService.deleteById(3L);

    // when
    entityManager.flush();
    entityManager.clear();

    // then
    Optional<Bamboo> result = bambooRepository.findById(3L);
    assertThat(result).isNotEmpty();
  }
}
