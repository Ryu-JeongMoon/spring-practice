package com.springservletthymeleaf.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LogController {

  @GetMapping("/log-test")
  public String log() {
    var name = "panda";
    log.trace("name = {}", name);
    log.debug("name = {}", name);
    log.info("name = {}", name);
    log.warn("name = {}", name);
    log.error("name = {}", name);

    return name;
  }
}

/*
log.info("name = " + name); 과 같이 사용하면 로그를 찍지 않는 경우에도 문자열 더하기 연산이 일어난다
문자열 연산은 많은 리소스를 사용하는 연산은 아니지만 분명 불필요한 연산이다
실무에서는 로그를 찍는 상황이 매우 많으니 불필요한 연산이 쌓여 리소스를 은근 잡아 먹을수도 있다?!
{}에 매핑되도록 만들어놨기 때문에 반드시 요런 방식으로 사용하도록 할 것
 */