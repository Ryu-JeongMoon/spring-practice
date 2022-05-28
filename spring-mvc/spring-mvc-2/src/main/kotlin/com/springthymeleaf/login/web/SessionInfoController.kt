package com.springthymeleaf.login.web

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpSession

@RestController
class SessionInfoController {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping("/session-info")
  fun printInfo(session: HttpSession): String {
    session.attributeNames
      .asIterator()
      .forEachRemaining { log.info("value = {}", session.getAttribute(it)) }

    log.info("session-id = {}", session.id)
    log.info("session-isNew = {}", session.isNew)
    log.info("session-creationTime = {}", Date(session.creationTime))
    log.info("session-lastAccessedTime = {}", Date(session.lastAccessedTime))
    log.info("session-maxInactiveInterval = {}", session.maxInactiveInterval)

    return "ok~!"
  }
}

/*
기본 HttpSession은 1800초, 30분 단위로 만료되며 매 요청마다 유효 시간이 30분으로 초기화된다
세션의 치명적인 단점은 서버의 리소스를 소모한다는 점이다
Http는 비연결성을 지향하므로 사용자가 로그아웃을 명시적으로 하지 않고 브라우저를 끈다면 유효 시간 동안 의미 없는 리소스가 소비된다
세션에 이것 저것 다 집어 넣다보면 서버의 메모리 사용량이 급격하게 상승할 수 있고
이를 막기 위해 최소한의 정보만 유지해야 한다

이런 단점이 있다고 해서 세션이 개쓰레기인건 아니고 보안적으로는 토큰보다 세션이 낫다리
다만 트래픽이 많은 서비스의 경우에는 리소스 사용량 & 확장성 문제로 인한 세션 동기화 때문에 세션 보다는 토큰이 더 유용하다
그래서 핫한 서비스의 경우 세션 클러스터링 방식 보다 토큰 방식을 선호하는 것임둥
 */