package com.springanything.index;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BearPandaService {

  private final BearPandaRepository bearPandaRepository;

  @Transactional
  public void save() {
    for (int i = 0; i < 3_000; i++) {
      BearPanda bearPanda = BearPanda.builder()
        .name1(UUID.randomUUID().toString().repeat(6))
        .name2(UUID.randomUUID().toString().repeat(6))
        .name3(UUID.randomUUID().toString().repeat(6))
        .build();
      bearPandaRepository.save(bearPanda);
    }
  }
}
