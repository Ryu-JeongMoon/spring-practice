package com.springthymeleaf.servlet

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class ErrorController {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping("/error")
  fun error() {
    throw RuntimeException("yahoo!")
  }

  @GetMapping("/error/404")
  fun error404(response: HttpServletResponse) {
    response.sendError(HttpStatus.NOT_FOUND.value(), "없둥")
  }

  @GetMapping("/error/500")
  fun error500(response: HttpServletResponse) {
    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "얘도 없둥")
  }

  @GetMapping("/error-page/ex")
  fun errorPageException(request: HttpServletRequest, response: HttpServletResponse): String {
    log.info("error!")
    printRequestInfo(request)

    return "error/5xx"
  }

  @GetMapping("/error-page/4xx")
  fun errorPage4xx(request: HttpServletRequest, response: HttpServletResponse): String {
    log.info("error!")
    printRequestInfo(request)

    return "error/4xx"
  }

  @GetMapping("/error-page/5xx")
  fun errorPage5xx(request: HttpServletRequest, response: HttpServletResponse): String {
    log.info("error!")
    printRequestInfo(request)

    return "error/5xx"
  }

  private fun printRequestInfo(request: HttpServletRequest) {
    log.info("ERROR_STATUS_CODE = {}", request.getAttribute(ERROR_STATUS_CODE))
    log.info("ERROR_EXCEPTION = {}", request.getAttribute(ERROR_EXCEPTION))
    log.info("ERROR_EXCEPTION_TYPE = {}", request.getAttribute(ERROR_EXCEPTION_TYPE))
    log.info("ERROR_MESSAGE = {}", request.getAttribute(ERROR_MESSAGE))
    log.info("ERROR_REQUEST_URI = {}", request.getAttribute(ERROR_REQUEST_URI))
    log.info("ERROR_SERVLET_NAME = {}", request.getAttribute(ERROR_SERVLET_NAME))
    log.info("DISPATCHER_TYPE = {}", request.dispatcherType)
  }

  companion object {
    private const val ERROR_STATUS_CODE = "javax.servlet.error.status_code"
    private const val ERROR_EXCEPTION = "javax.servlet.error.exception"
    private const val ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type"
    private const val ERROR_MESSAGE = "javax.servlet.error.message"
    private const val ERROR_REQUEST_URI = "javax.servlet.error.request_uri"
    private const val ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name"
  }
}

/*
Controller에서 발생한 예외를 WAS까지 전파시키면 어떻게 될까?
- 기본 error 매핑으로 예외 터트리니까 stacktrace 전부 다 훑어줌
- 404, 500으로 response.sendError로 내려주면 상태 코드, 사유 구절까지만 알려줌

WAS 특별한 처리까지는 안 해주고 상태 코드, 사유 구절, 스택 트레이스 있다면 얘까지 그냥 내려주기만 함
얘로만 처리하면 굉장히 허술한 사이트 같고 심각한 경우 스택 트레이스로 역추적해 구조까지 파악할 수 있다
 */