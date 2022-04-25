package org.example.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import lombok.RequiredArgsConstructor;
import org.example.Panda;
import org.example.service.HelloService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {

  private final HelloService helloService;

  @CrossOrigin(origins = "http://localhost:18080")
  @GetMapping("/hello-panda")
  public String hello() {
    return helloService.getDeclaration();
  }

  @PostMapping("/panda/create")
  @ResponseStatus(value = HttpStatus.CREATED)
  public Panda panda(@RequestBody Panda panda) {
    return helloService.getPanda();
  }

  @GetMapping("/panda-hello")
  public EntityModel<Panda> getHateoasPanda() {
    return EntityModel.of(
      new Panda("hello", 15),
      linkTo(methodOn(HelloController.class).getHateoasPanda()).withSelfRel()
    );
  }
}
