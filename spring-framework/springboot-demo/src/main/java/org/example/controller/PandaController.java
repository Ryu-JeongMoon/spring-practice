package org.example.controller;

import static java.lang.Thread.sleep;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PandaController {

  @GetMapping("/hello")
  public String hello() {
    return "hello panda bear".repeat(55);
  }

  @GetMapping("/panda")
  public String panda() {
    sleepMan(1000);

    return "panda";
  }

  @GetMapping("/bear")
  public String bear() {
    sleepMan(2000);

    return "bear";
  }

  private void sleepMan(int millis) {
    try {
      sleep(millis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

/*
curl -I -k --http2 https://localhost:<port>/hello
header 만 가져오너라

curl -k --http2 https://localhost:<port>/hello
body 도 가져오너라
 */