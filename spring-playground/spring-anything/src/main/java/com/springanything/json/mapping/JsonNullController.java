package com.springanything.json.mapping;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonNullController {

  @PostMapping("/test-json-null")
  public String testJsonNull(@RequestBody JsonNullRequest request) {
    List<String> names = request.getNames();
    if (names == null) {
      return "names is null";
    } else if (names.isEmpty()) {
      return "names is empty";
    } else {
      return String.join(", ", names);
    }
  }
}
