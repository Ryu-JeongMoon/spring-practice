package com.springanything.jpa.onetomany;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractRepositoryTest;

class PandaPriceRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private PriceRepository priceRepository;

  @Test
  void save() {
    // given
    List<PandaOptionGroup> pandaOptionGroups = List.of(
      new PandaOptionGroup("1", 1000),
      new PandaOptionGroup("2", 2000),
      new PandaOptionGroup("3", 3000)
    );
    PandaPrice pandaPrice = priceRepository.save(new PandaPrice(pandaOptionGroups));
    flushAndClear();

    // when
    PandaPrice found = priceRepository.findById(pandaPrice.getId()).orElseThrow();
    found.update(List.of(
      new PandaOptionGroup("4", 4000),
      new PandaOptionGroup("5", 5000),
      new PandaOptionGroup("6", 6000)
    ));
    flushAndClear();

    // then
    PandaPrice result = priceRepository.findById(found.getId()).orElseThrow();
    System.out.println("result = " + result);
    System.out.println("result = " + result.getPandaOptionGroups());
  }
}
