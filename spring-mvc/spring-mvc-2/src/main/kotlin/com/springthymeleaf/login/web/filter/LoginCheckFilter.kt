package com.springthymeleaf.login.web.filter

import com.springthymeleaf.login.web.login.LOGIN_MEMBER
import org.slf4j.LoggerFactory
import org.springframework.util.PatternMatchUtils
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class LoginCheckFilter : OncePerRequestFilter() {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun shouldNotFilter(request: HttpServletRequest): Boolean {
    return PatternMatchUtils.simpleMatch(WHITE_LIST, request.requestURI)
  }

  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    val session = request.session
    val requestURI = request.requestURI

    if (session?.getAttribute(LOGIN_MEMBER) == null) {
      log.info("미인증 사용자 요청 {}", requestURI)
      response.sendRedirect("/login?redirectURI=$requestURI")
      return
    }

    filterChain.doFilter(request, response)
  }
}
