package com.springservlet.web.springmvc.interfacebased;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

@Slf4j
@Component("/spring-mvc/request-handler")
public class MvcHttpRequestHandler implements HttpRequestHandler {

  @Override
  public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
    log.info("MvcHttpRequestHandler.handleRequest");
  }
}
