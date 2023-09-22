package com.springanything.jpa.onetone;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractRepositoryTest;

class MasterRepositoryImplTest extends AbstractRepositoryTest {

  @Autowired
  private MasterRepository masterRepository;

  private Long firstMasterId;
  private Long secondMasterId;

  private Long firstSlaveId;
  private Long secondSlaveId;

  @BeforeEach
  void setUp() {
    Slave slave1 = Slave.builder()
      .name("slave1")
      .build();

    Slave slave2 = Slave.builder()
      .name("slave2")
      .build();

    entityManager.persist(slave1);
    entityManager.persist(slave2);

    firstSlaveId = slave1.getId();
    secondSlaveId = slave2.getId();

    Master master1 = Master.builder()
      .slave(slave1)
      .build();

    Master master2 = Master.builder()
      .slave(slave2)
      .build();

    masterRepository.saveAll(List.of(master1, master2));

    firstMasterId = master1.getId();
    secondMasterId = master2.getId();
  }

  @DisplayName("queryFactory 연관 관계까지 지워주지는 않는다")
  @Test
  void deleteByIds() {
    // given
    List<Long> ids = List.of(firstMasterId, secondMasterId);

    // when
    masterRepository.deleteByIds(ids);
    entityManager.flush();
    entityManager.clear();

    // then
    List<Master> result = masterRepository.findAllById(ids);
    assertThat(result).isEmpty();
  }

  @DisplayName("deleteById 연관 관계까지 제거")
  @Test
  void delete() {
    // given
    masterRepository.deleteById(firstMasterId);

    // when
    entityManager.flush();
    entityManager.clear();

    // then
    Slave slave = entityManager.find(Slave.class, firstSlaveId);
    assertThat(slave).isNull();
  }
}
