package com.springthymeleaf.exception.resolver

import com.fasterxml.jackson.databind.ObjectMapper
import com.springthymeleaf.exception.CustomException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomExceptionHandler : HandlerExceptionResolver {

  private val log = LoggerFactory.getLogger(javaClass)
  private val objectMapper = ObjectMapper()

  override fun resolveException(request: HttpServletRequest, response: HttpServletResponse, handler: Any?, ex: Exception): ModelAndView? {
    try {
      if (ex is CustomException) {
        log.error("custom ex = ", ex)

        val acceptHeader = request.getHeader(HttpHeaders.ACCEPT)
        response.status = HttpServletResponse.SC_BAD_REQUEST

        return if (acceptHeader == MediaType.APPLICATION_JSON_VALUE) {
          val errorResult = mapOf(Pair("exception_class", ex.javaClass), Pair("exception_message", ex.message))
          val result = objectMapper.writeValueAsString(errorResult)

          response.contentType = MediaType.APPLICATION_JSON_VALUE
          response.characterEncoding = StandardCharsets.UTF_8.name()
          response.writer.write(result)
          ModelAndView()
        } else {
          ModelAndView("error/5xx")
        }
      }
    } catch (e: IOException) {
      log.error("exception!", e)
    }

    return null
  }
}

/*
response content-type, character-encoding 세팅 후 빈 ModelAndView 를 넘기는 것은
예외를 여기서 먹어버리고 정상 흐름으로 값을 내려주기 위함

 */