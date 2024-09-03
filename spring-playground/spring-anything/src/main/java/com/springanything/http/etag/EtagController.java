package com.springanything.http.etag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EtagController {

  private final EtagRepository etagRepository;

  @GetMapping("/etag")
  public EtagEntity get(@RequestParam String name) {
    return etagRepository.findByName(name).orElseThrow();
  }

  @PostMapping("/etag")
  public EtagEntity save(@RequestBody EtagEntity etagEntity) {
    return etagRepository.save(etagEntity);
  }
}
