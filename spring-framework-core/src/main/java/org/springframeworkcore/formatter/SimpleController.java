package org.springframeworkcore.formatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

  @GetMapping("/hello/{person}")
  public String hello(@PathVariable Person person) {
    return "hello " + person.getName();
  }

  @GetMapping("/message")
  public String message(@RequestBody String body) {
    return body;
  }

  @GetMapping("/json")
  public Person jsonMessage(@RequestBody Person person) {
    return person;
  }
}
