package com.springthymeleaf.login.web.login

import com.springthymeleaf.login.domain.login.LoginService
import com.springthymeleaf.login.web.session.SessionManager
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class LoginControllerV2(
  private val loginService: LoginService,
  private val loginRequestMapper: LoginRequestMapper,
  private val sessionManager: SessionManager
) {

  //  @PostMapping("/login")
  fun loginWithSession(
    @Validated @ModelAttribute loginRequest: LoginRequest,
    bindingResult: BindingResult,
    response: HttpServletResponse
  ): String {
    if (bindingResult.hasErrors())
      return "login/login-form"

    val member = loginRequestMapper.toEntity(loginRequest)
    val loginMember = loginService.login(member)
    loginMember ?: bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않슴둥")

    loginMember?.let { sessionManager.create(loginMember, response) }
    return "redirect:/"
  }

  //  @PostMapping("/logout")
  fun logoutWithSession(request: HttpServletRequest): String {
    sessionManager.expire(request)
    return "redirect:/"
  }
}

/*
Cookie 보낼 때 유효시간 설정 안 해주면 session-cookie로 사용된다
세션이 종료되면, 즉 브라우저가 완전히 종료되면 쿠키는 날라간다
 */