package org.example.service;

import org.example.Panda;
import org.springframework.stereotype.Service;

@Service
public class HelloService {


  public String getDeclaration() {
    return "hello springboot !";
  }

  public Panda getPanda() {
    return new Panda("불곰", 15);
  }
}
