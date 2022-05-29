package com.springthymeleaf.login.web.annotation

import com.springthymeleaf.login.domain.member.Member
import com.springthymeleaf.login.web.login.LOGIN_MEMBER
import org.slf4j.LoggerFactory
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

class LoginMemberArgumentResolver : HandlerMethodArgumentResolver {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun supportsParameter(parameter: MethodParameter): Boolean {
    log.info("EXECUTE [supportsParameter]")

    val hasAnnotation = parameter.hasParameterAnnotation(LoginMember::class.java)
    val hasMemberType = Member::class.java.isAssignableFrom(parameter.parameterType)

    return hasAnnotation && hasMemberType
  }

  override fun resolveArgument(
    parameter: MethodParameter,
    mavContainer: ModelAndViewContainer?,
    webRequest: NativeWebRequest,
    binderFactory: WebDataBinderFactory?
  ): Any? {
    log.info("EXECUTE [resolveArgument]")

    val httpServletRequest = webRequest.nativeRequest as HttpServletRequest
    val session = httpServletRequest.session
    return session?.let { session.getAttribute(LOGIN_MEMBER) }
  }
}