package com.springanything.jpa;

import java.util.List;

import org.hibernate.SharedSessionContract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PandaService {

  private final PandaRepository pandaRepository;

  @Transactional(readOnly = true)
  public List<Panda> findByBambooId(Long bambooId) {
    return pandaRepository.findByBambooId(bambooId);
  }

  @Transactional
  public void deleteByBearId(Long bearId, SharedSessionContract session) {
    pandaRepository.deleteByBearId(bearId, session);
  }

}
