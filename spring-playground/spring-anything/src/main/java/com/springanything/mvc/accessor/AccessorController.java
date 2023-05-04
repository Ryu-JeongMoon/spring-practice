package com.springanything.mvc.accessor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AccessorController {

  @GetMapping("/test-accessor")
  public AccessorRequest access(AccessorRequest accessorRequest) {
    log.info("accessorRequest: {}", accessorRequest);
    return accessorRequest;
  }
}
