package com.springanything.jpa.context;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReReadableService {

  private final ReReadableEntityRepository repository;

  @Transactional
  public ReReadableEntity save(ReReadableEntity entity) {
    return repository.save(entity);
  }

  @Transactional(readOnly = true)
  public ReReadableEntity findById(String id) {
    ReReadableEntity first = findOrThrow(id);
    log.info("first: {}", first);

    ReReadableEntity second = findOrThrow(id);
    log.info("second: {}", second);

    return findOrThrow(id);
  }

  private ReReadableEntity findOrThrow(String id) {
    return repository.findById(id).orElseThrow();
  }
}
