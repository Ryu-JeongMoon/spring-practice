package com.springanything.mapping.nested;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NestedMappingController {

  @PostMapping("/nested-class-mapping")
  public OuterClass outerClass(@RequestBody OuterClass outerClass) {
    return outerClass;
  }

  @PostMapping("/nested-record-mapping")
  public OuterRecord outerRecord(@RequestBody OuterRecord outerRecord) {
    return outerRecord;
  }
}
