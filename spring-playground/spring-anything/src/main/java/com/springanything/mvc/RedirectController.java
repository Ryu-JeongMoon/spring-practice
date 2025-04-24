package com.springanything.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectController {

  @GetMapping("/redirect")
  public RedirectView redirect() {
    return new RedirectView("https://spring.io");
  }
}
