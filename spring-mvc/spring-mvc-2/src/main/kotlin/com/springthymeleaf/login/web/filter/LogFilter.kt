package com.springthymeleaf.login.web.filter

import org.slf4j.LoggerFactory
import org.springframework.util.PatternMatchUtils
import java.util.*
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

val WHITE_LIST: Array<String> = arrayOf(
  "/", "/members/add", "/login",
  "/logout", "/css/**", "/error/**", "/error-page/**"
)

class LogFilter : Filter {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
    val httpServletRequest = request as HttpServletRequest
    val requestURI = httpServletRequest.requestURI
    val dispatcherType = httpServletRequest.dispatcherType
    val uuid = UUID.randomUUID().toString()

    if (PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI)) {
      chain?.doFilter(request, response)
      return
    }

    try {
      log.info("REQUEST [{}][{}][{}]", uuid, dispatcherType, requestURI)
      chain?.doFilter(request, response)
    } catch (e: java.lang.Exception) {
      throw e
    } finally {
      log.info("RESPONSE [{}][{}][{}]", uuid, dispatcherType, requestURI)
    }
  }
}

/*
애플리케이션 전체 혹은 넓은 범위에 적용시킬 공통의 관심사를 Controller에서 직접 구현한다면 매우 번거로움둥
이걸 따로 떼어낼 수 있지 않을까? 하는 생각에서 AOP로 처리할 수 있고,
웹과 관련된 요청은 그보다 앞단에서 Servlet이 들어오기 전에 수행할 수 있는디 고것이 Servlet Filter 임둥
HttpRequest -> Filter -> Servlet -> Spring Interceptor -> Controller의 흐름
인증 관련 공통 관심사는 주로 Filter or Interceptor에서 처리해뿐다

왜 Filter를 쓰는 것이 좋은가?
로그인 여부, 권한 체크 등은 조건이 충족되지 않는다면 요청을 처리할 Handler까지 전달할 필요가 없다
따라서 Filter에서 확인 후 Servlet으로 넘겨주지도 않는 형태로 만드는 것이 리소스 효율 면에서 좋다리
여러가지 Filter들은 Filter Chain으로 구성되는디 Spring-Security 또한 체이닝하여 구성되어 있고
커스터마이징으로 사이사이에 직접 만든 체인을 박아넣을 수도 있다
FilterRegistrationBean을 사용해 등록해도 되고 Filter 인터페이스의 구현체를 상속 받고 @Bean으로 등록할 수도 있당
ex) extends OncePerRequestFilter, 요놈 좀 더 알아볼 것

interface에 default 키워드 추가 되면서 init, destroy는 안 만들어줘도 된다
기본 구현은 아무것도 없는 상태임둥
 */