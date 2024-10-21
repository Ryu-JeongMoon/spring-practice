package com.springanything.jpa.onetomany;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractRepositoryTest;

class BearPriceRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private BearPriceRepository bearPriceRepository;

  @Test
  void save() {
    // given
    List<BearOptionGroup> bearOptionGroups = List.of(
      new BearOptionGroup("1", 1000),
      new BearOptionGroup("2", 2000),
      new BearOptionGroup("3", 3000)
    );
    BearPrice bearPrice = bearPriceRepository.save(new BearPrice(bearOptionGroups));
    flushAndClear();

    // when
    BearPrice found = bearPriceRepository.findById(bearPrice.getId()).orElseThrow();
    found.update(List.of(
      new BearOptionGroup("4", 4000),
      new BearOptionGroup("5", 5000),
      new BearOptionGroup("6", 6000)
    ));
    flushAndClear();

    // then
    BearPrice result = bearPriceRepository.findById(found.getId()).orElseThrow();
    System.out.println("result = " + result);
    System.out.println("result = " + result.getBearOptionGroups());
  }
}
