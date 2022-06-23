package com.springanything.mapping;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TestViewerController {

  @GetMapping("/test-view")
  public String testView(Model model) {
    TestRequest testRequest = new TestRequest(null, 0);
    model.addAttribute("testRequest", testRequest);
    return "test-view";
  }

  @ResponseBody
  @PostMapping(value = "/test/request-body")
  public TestRequest testRequestBody(@RequestBody TestRequest testRequest) {
    return testRequest;
  }

  @ResponseBody
  @PostMapping(value = "/test/model-attribute", consumes = MediaType.ALL_VALUE)
  public TestRequest testModelAttribute(TestRequest testRequest) {
    log.info("testRequest = {}", testRequest);
    return testRequest;
  }
}
