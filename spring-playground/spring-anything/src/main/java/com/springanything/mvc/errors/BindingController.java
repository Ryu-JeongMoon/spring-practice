package com.springanything.mvc.errors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BindingController {

  private String handle(BindingRequest bindingRequest, BindingResult bindingResult, String host, HttpServletRequest servletRequest) {
    log.info("host : {}", host);
    log.info("bindingRequest: {}", bindingRequest);
    log.info("ServletContext : {}", servletRequest.getServletContext());

    if (bindingResult.hasErrors()) {
      log.info("bindingResult: {}", bindingResult);
      return "no~~";
    }

    return "ok~~";
  }

  @GetMapping("/binding-sample-1")
  public String bindingSample1(
    @Valid BindingRequest bindingRequest,
    BindingResult bindingResult,
    @RequestParam String host,
    HttpServletRequest servletRequest
  ) {
    return handle(bindingRequest, bindingResult, host, servletRequest);
  }

  @GetMapping("/binding-sample-2")
  public String bindingSample2(
    @Valid BindingRequest bindingRequest,
    @RequestParam String host,
    HttpServletRequest servletRequest,
    BindingResult bindingResult
  ) {
    return handle(bindingRequest, bindingResult, host, servletRequest);
  }
}

/*
sample-1
@Valid 붙인 객체 바로 뒤에 BindingResult 붙여야 개발자가 의도한 흐름으로 수행된다

sample-2
그렇지 않은 경우, BindingResult 자체와 400 에러를 반환한다
 */
