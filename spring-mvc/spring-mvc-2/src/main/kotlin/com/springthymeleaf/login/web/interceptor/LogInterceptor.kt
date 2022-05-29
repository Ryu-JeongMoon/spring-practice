package com.springthymeleaf.login.web.interceptor

import org.slf4j.LoggerFactory
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val LOG_ID = "log-id"

class LogInterceptor : HandlerInterceptor {

  private val log = LoggerFactory.getLogger(javaClass)

  // true 반환 시에만 다음으로 진행된다
  override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
    val requestURI = request.requestURI
    val logId = UUID.randomUUID().toString()
    request.setAttribute(LOG_ID, logId)

    if (handler is HandlerMethod) {
      log.info("HANDLER [bean] = {}", handler.bean)
      log.info("HANDLER [method] = {}", handler.method)
      log.info("HANDLER [beanType] = {}", handler.beanType)
      log.info("HANDLER [returnType] = {}", handler.returnType)
      log.info("HANDLER [shortLogMessage] = {}", handler.shortLogMessage)
      log.info("HANDLER [methodParameters] = {}", handler.methodParameters)
      log.info("HANDLER [resolvedFromHandlerMethod] = {}", handler.resolvedFromHandlerMethod)
    }

    log.info("REQUEST [{}][{}][{}]", logId, requestURI, handler)
    return super.preHandle(request, response, handler)
  }

  // 정상 플로우에서만 호출됨
  override fun postHandle(
    request: HttpServletRequest,
    response: HttpServletResponse,
    handler: Any,
    modelAndView: ModelAndView?
  ) {
    log.info("model and view = {}", modelAndView)
  }

  // finally 같은 놈
  override fun afterCompletion(
    request: HttpServletRequest,
    response: HttpServletResponse,
    handler: Any,
    ex: Exception?
  ) {
    val requestURI = request.requestURI
    val logId = request.getAttribute(LOG_ID)
    log.info("RESPONSE [{}][{}]", logId, requestURI)

    ex?.let { log.error("afterCompletion error!", ex) }
  }
}