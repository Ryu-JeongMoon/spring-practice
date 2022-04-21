package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PandaController {

  @GetMapping("/hello")
  public String hello() {
    return "hello panda bear".repeat(55);
  }
}

/*
curl -I -k --http2 https://localhost:<port>/hello
header 만 가져오너라

curl -k --http2 https://localhost:<port>/hello
body 도 가져오너라
 */