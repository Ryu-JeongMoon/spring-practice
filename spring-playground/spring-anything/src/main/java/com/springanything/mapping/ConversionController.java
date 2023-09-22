package com.springanything.mapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConversionController {

  @GetMapping("/test-conversion")
  public String convert(@RequestParam String param) {
    return param;
  }

  @PostMapping("/test-body-inner-conversion")
  @ResponseBody
  public OuterRequest convertInnerBody(@RequestBody OuterRequest request) {
    return request;
  }

  @PostMapping("/test-model-inner-conversion")
  @ResponseBody
  public OuterRequest convertInnerModel(@ModelAttribute OuterRequest request) {
    return request;
  }
}
