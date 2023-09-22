package com.springanything.mapping.nested;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NestedSetterService {

  private final NestedSetterRequestRepo nestedSetterRequestRepo;

  public NestedSetterRequest get(String id) {
    return nestedSetterRequestRepo.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("id = " + id));
  }

  public NestedSetterRequest findByInnerName(String name) {
    return nestedSetterRequestRepo.findByInnerName(name)
      .orElseThrow(() -> new IllegalArgumentException("name = " + name));
  }

  @Transactional
  public NestedSetterRequest save(NestedSetterRequest request) {
    return nestedSetterRequestRepo.save(request);
  }

  @Transactional
  public NestedSetterRequest patch(String id, NestedSetterRequest request) {
    return nestedSetterRequestRepo.findById(id)
      .orElseGet(() -> nestedSetterRequestRepo.save(request))
      .update(request);
  }
}
