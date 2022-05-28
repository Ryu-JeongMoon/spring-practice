package com.springthymeleaf.login.web.session

import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private const val SESSION_ID = "session-id"

@Component
class SessionManager {

  private val sessionStorage = ConcurrentHashMap<String, Any>()

  fun create(value: Any, response: HttpServletResponse) {
    val sessionId = UUID.randomUUID().toString()
    sessionStorage[sessionId] = value

    val cookie = Cookie(SESSION_ID, sessionId)
    response.addCookie(cookie)
  }

  fun get(request: HttpServletRequest): Any? {
    return findCookie(request)?.let { sessionStorage[it.value] }
  }

  private fun findCookie(request: HttpServletRequest): Cookie? {
    return request.cookies?.find { it.name.equals(SESSION_ID) }
  }

  fun expire(request: HttpServletRequest) {
    findCookie(request)?.let { sessionStorage.remove(it.value) }
  }
}