package com.springanything.jpa.wrapper;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicService {

  private final HiddenRepository hiddenRepository;

  public UUID create() {
    return hiddenRepository.save(new InnerDomain()).getId();
  }
}
