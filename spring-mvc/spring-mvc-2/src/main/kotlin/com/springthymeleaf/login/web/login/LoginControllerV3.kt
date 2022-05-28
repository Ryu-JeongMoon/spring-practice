package com.springthymeleaf.login.web.login

import com.springthymeleaf.login.domain.login.LoginService
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.http.HttpServletRequest

@Controller
class LoginControllerV3(
  private val loginService: LoginService,
  private val loginRequestMapper: LoginRequestMapper
) {

  @PostMapping("/login")
  fun loginWithSession(
    @Validated @ModelAttribute loginRequest: LoginRequest,
    bindingResult: BindingResult,
    request: HttpServletRequest
  ): String {
    if (bindingResult.hasErrors())
      return "login/login-form"

    val member = loginRequestMapper.toEntity(loginRequest)
    val loginMember = loginService.login(member)
    loginMember ?: bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않슴둥")

    loginMember?.let { request.session.setAttribute(LOGIN_MEMBER, loginMember) }
    return "redirect:/"
  }

  @PostMapping("/logout")
  fun logoutWithSession(request: HttpServletRequest): String {
    request.getSession(false)?.invalidate()
    return "redirect:/"
  }
}

/*
Cookie 보낼 때 유효시간 설정 안 해주면 session-cookie로 사용된다
세션이 종료되면, 즉 브라우저가 완전히 종료되면 쿠키는 날라간다

HttpSession은 직접 만든 SessionManager와 같은 형태로 동작한다
JSESSIONID 라는 이름으로 쿠키를 만들어넣고 요놈을 이용해 저장된 세션을 꺼내오고 세션에서 여러 정보를 뽑아낼 수 있음둥
 */