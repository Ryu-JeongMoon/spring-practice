package com.springanything.json.conversion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonConversionController {

  @GetMapping("/test-json-boolean-to-int")
  public JsonBooleanRequest testJsonBoolean(@ModelAttribute JsonBooleanRequest request) {
    return request;
  }

  @PostMapping("/test-json-boolean-to-int")
  public JsonBooleanRequest testJsonBooleanPost(@RequestBody JsonBooleanRequest request) {
    return request;
  }
}
