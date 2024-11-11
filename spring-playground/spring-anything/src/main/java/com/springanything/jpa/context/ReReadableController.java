package com.springanything.jpa.context;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReReadableController {

  private final ReReadableService reReadableService;

  @PostMapping("/test/re-readables")
  public ReReadableEntity save(@RequestParam String name) {
    ReReadableEntity entity = new ReReadableEntity(name);
    return reReadableService.save(entity);
  }

  @GetMapping("/test/re-readables/{id}")
  public ReReadableEntity findById(@PathVariable String id) {
    return reReadableService.findById(id);
  }
}
