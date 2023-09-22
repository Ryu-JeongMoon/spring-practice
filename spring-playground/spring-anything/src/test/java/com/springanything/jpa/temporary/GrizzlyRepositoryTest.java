package com.springanything.jpa.temporary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractRepositoryTest;

class GrizzlyRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private GrizzlyRepository grizzlyRepository;

  @DisplayName("Transient 유지 되는가?")
  @Test
  void save() {
    // given
    Grizzly grizzly = Grizzly.builder()
      .name("white-bear")
      .build();
    grizzly.setName("grizzly");

    // when
    Grizzly savedGrizzly = grizzlyRepository.save(grizzly);

    // then
    System.out.println("savedGrizzly.isNameChanged() = " + savedGrizzly.isNameChanged());
  }
}
