package com.springservlet.web.springmvc.interfacebased;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@Slf4j
@Component("/spring-mvc/interface-based")
public class InterfaceBasedController implements Controller {

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
    log.info("InterfaceBasedController.handleRequest");
    return new ModelAndView("new-form");
  }
}
