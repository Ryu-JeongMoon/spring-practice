package com.springanything.mvc.primitive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PrimitiveController {

  /**
   * @ModelAttribute로 매핑 시 0 & false -> false, 1 & true -> true
   */
  @GetMapping("/test/primitive")
  public BooleanRequest booleanRequest(@ModelAttribute BooleanRequest booleanRequest) {
    log.info("booleanRequest: {}", booleanRequest);
    return booleanRequest;
  }
}
