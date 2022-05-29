package com.springthymeleaf.login.web.interceptor

import com.springthymeleaf.login.web.login.LOGIN_MEMBER
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoginCheckInterceptor : HandlerInterceptor {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
    val session = request.session
    val requestURI = request.requestURI

    session?.getAttribute(LOGIN_MEMBER)
      ?.let { return true }
      ?: let {
        log.info("미인증 사용자 요청 {}", requestURI)
        response.sendRedirect("/login?redirectURI=$requestURI")
        return false
      }
  }
}

/*
가독성이 좋지 않은 듯함..
let chain 별로인감?
 */