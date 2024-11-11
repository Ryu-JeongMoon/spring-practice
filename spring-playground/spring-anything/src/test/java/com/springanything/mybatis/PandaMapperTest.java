package com.springanything.mybatis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractIntegrationTest;
import com.springanything.jpa.Bamboo;
import com.springanything.jpa.Bear;
import com.springanything.jpa.BearRepository;
import com.springanything.jpa.Panda;

class PandaMapperTest extends AbstractIntegrationTest {

  @Autowired
  private PandaMapper pandaMapper;

  @Autowired
  private BearRepository bearRepository;

  @Test
  void save() {
    // given
    Bamboo bamboo = Bamboo.builder()
      .type("delicious")
      .build();

    Panda panda = Panda.builder()
      .bearId(1L)
      .bamboo(bamboo)
      .age(24)
      .name("kind")
      .weight(100)
      .build();

    pandaMapper.save(panda);

    // when
    Bear bear = Bear.builder()
      .region("Asia")
      .build();

    bearRepository.save(bear);

    // then
    flushAndClear();
  }
}

