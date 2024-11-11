package com.springanything.jpa.custom_id;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractIntegrationTest;

class CustomBambooRepoTest extends AbstractIntegrationTest {

  @Autowired
  private CustomBambooRepo customBambooRepo;

  @Test
  void saveUniquely() {
    // given
    int iteration = 50;
    HashSet<String> uniqueIds = new HashSet<>(iteration);

    // when
    for (int i = 0; i < iteration; i++) {
      CustomBamboo bamboo = CustomBamboo.builder()
        .name("BIG %s BAMBOO".formatted(i))
        .build();

      uniqueIds.add(customBambooRepo.save(bamboo).getId());
    }

    // then
    assertThat(uniqueIds.size()).isEqualTo(iteration);
  }
}
