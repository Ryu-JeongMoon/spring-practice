package com.springanything.index;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BearPandaController {

  private final BearPandaService bearPandaService;

  @RequestMapping("/bear-panda")
  public ResponseEntity<Void> bearPanda() {
    bearPandaService.save();
    return ResponseEntity.created(URI.create("")).build();
  }
}
