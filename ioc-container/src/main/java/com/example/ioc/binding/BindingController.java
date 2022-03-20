package com.example.ioc.binding;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BindingController {

//  @InitBinder
//  public void init(WebDataBinder webDataBinder) {
//    webDataBinder.registerCustomEditor(BindingObject.class, new BindingObjectEditor());
//  }

  @GetMapping("/bind/{bindingObject}")
  public String getBindingObject(@PathVariable BindingObject bindingObject) {
    System.out.println("bindingObject = " + bindingObject);
    return bindingObject.getId().toString();
  }
}
