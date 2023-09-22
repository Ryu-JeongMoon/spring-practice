package com.springanything.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiddleController {

  /* uri 중간에 매핑해도 오께이 */
  @GetMapping("/test/{vdiAgentId}/panda")
  public String panda(@PathVariable String vdiAgentId) {
    return "panda-%s".formatted(vdiAgentId);
  }
}
