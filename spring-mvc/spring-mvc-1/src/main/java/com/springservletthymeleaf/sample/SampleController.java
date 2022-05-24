package com.springservletthymeleaf.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @PostMapping(
    path = "/yahoo",
    consumes = { MediaType.ALL_VALUE },
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Yahoo yahoo(@ModelAttribute Yahoo yahoo) {
    log.info("yahoo = {}", yahoo);
    return yahoo;
  }
}
