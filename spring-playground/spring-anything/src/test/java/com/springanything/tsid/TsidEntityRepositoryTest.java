package com.springanything.tsid;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractRepositoryTest;

class TsidEntityRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private TsidEntityRepository tsidEntityRepository;

  @DisplayName("saveAll()을 사용해 id 생성해도 중복 발생하지 않음")
  @RepeatedTest(5)
  void saveAll() {
    // given
    List<TsidEntity> entities = new ArrayList<>();
    for (int i = 0; i < 1_000; i++) {
      entities.add(new TsidEntity());
    }

    // when
    List<TsidEntity> saved = tsidEntityRepository.saveAll(entities);
    long count = saved.stream()
      .map(TsidEntity::getId)
      .distinct()
      .count();

    // then
    assertThat(saved.size()).isEqualTo(count);
  }
}
