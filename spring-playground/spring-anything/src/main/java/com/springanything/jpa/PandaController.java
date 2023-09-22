package com.springanything.jpa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PandaController {

  private final PandaRepository pandaRepository;
  private final BearRepository bearRepository;
  private final BambooRepository bambooRepository;

  private final BearService bearService;

  @GetMapping("/panda")
  public Panda panda() {
    return pandaRepository.findPandaType(1L).orElseThrow();
  }

  @GetMapping("/panda-response")
  public PandaResponse pandaResponse() {
    return pandaRepository.findType(1L).orElseThrow();
  }

  @GetMapping("/panda-child")
  public PandaChild pandaChild() {
    return pandaRepository.findChild(1L).orElseThrow();
  }

  @PostMapping("/panda")
  public Panda save(@RequestParam Long sequence) {
    Bamboo bamboo = Bamboo.builder()
      .type("bear%d".formatted(sequence))
      .build();
    bambooRepository.save(bamboo);

    Bear bear = Bear.builder()
      .region("korea%d".formatted(sequence))
      .build();
    bearRepository.save(bear);

    Panda panda = Panda.builder()
      .name("panda%d".formatted(sequence))
      .age(sequence.intValue())
      .bamboo(bamboo)
      .bearId(bear.getId())
      .build();
    return pandaRepository.save(panda);
  }

  @DeleteMapping("/bear/delete")
  public ResponseEntity<Void> deleteBear(@RequestParam Long bearId) {
    bearService.deleteById(bearId);
    log.info("deleteBear.thread : {}", Thread.currentThread().getId());
    return ResponseEntity.noContent().build();
  }
}
