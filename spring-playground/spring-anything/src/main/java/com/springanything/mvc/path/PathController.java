package com.springanything.mvc.path;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PathController {

  @GetMapping("/test/path")
  public String path() {
    return "ok";
  }

  @GetMapping("/test/path/")
  public String pathWithTrailingSlash() {
    return "ok/";
  }

  @GetMapping(value = "/test/path.*", produces = { MediaType.ALL_VALUE })
  public String pathWithSuffix() {
    return "json-ok";
  }
}
