package com.springthymeleaf.login.web

import com.springthymeleaf.login.domain.member.Member
import com.springthymeleaf.login.domain.member.MemberRepository
import com.springthymeleaf.login.web.annotation.LoginMember
import com.springthymeleaf.login.web.login.LOGIN_MEMBER
import com.springthymeleaf.login.web.session.SessionManager
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.SessionAttribute
import javax.servlet.http.HttpServletRequest

@Controller
class HomeController(
  private val memberRepository: MemberRepository,
  private val sessionManager: SessionManager
) {

  private val log = LoggerFactory.getLogger(javaClass)

  //  @GetMapping("/")
  fun home(): String {
    return "home"
  }

  //  @GetMapping("/")
  fun loginHome(@CookieValue(value = "memberId", required = false) id: Long?, model: Model): String {
    id ?: return "home"

    val member = memberRepository.findById(id).orElse(null)
    member ?: return "home"

    model.addAttribute("member", member)
    return "login/login-home"
  }

  //  @GetMapping("/")
  fun loginHomeV2(request: HttpServletRequest, model: Model): String {
    val sessionValue = sessionManager.get(request)
    sessionValue ?: return "home"

    model.addAttribute("member", sessionValue)
    return "login/login-home"
  }

  //  @GetMapping("/")
  fun loginHomeV3(request: HttpServletRequest, model: Model): String {
    val session = request.getSession(false)
    session ?: return "home"

    val loginMember = session.getAttribute(LOGIN_MEMBER) as? Member
    log.info("member = {}", loginMember)

    loginMember?.let { model.addAttribute("member", loginMember) }

    return "login/login-home"
  }

  //  @GetMapping("/")
  fun loginHomeV3WithSpring(
    @SessionAttribute(name = LOGIN_MEMBER, required = false) loginMember: Member?,
    model: Model
  ): String {

    loginMember?.let { model.addAttribute("member", loginMember) } ?: let { return "home" }
    return "login/login-home"
  }

  @GetMapping("/")
  fun loginHomeV3WithArgumentResolver(
    @LoginMember loginMember: Member?,
    model: Model
  ): String {

    loginMember?.let { model.addAttribute("member", loginMember) } ?: let { return "home" }
    return "login/login-home"
  }
}

/*
@CookieValue로 쿠키 바로 뽑아낼 수 있당
session 관리자를 직접 만들어 Pair<SessionId, Member> 형태로 집어넣어준다
조회 시에는 세션 아이디로 찾을 수 있도록 한다

elvis operator, null-safe operator 사용하면서 간결하게 사용은 할 수 있으나 명확하게 표현이 되는지는 모르겠다
명확성 측면에서는 자바로 조금 길게 짜더라도 확실하게 보여주는 것이 나을지도?!
지금 작성한 예시는 단순히 코드만 짧아지는 형태인 것 같고 코틀린스럽게 짠 것 같지는 않음둥..
코틀린에서는 어떻게 작성한 것이 좋은 것일까?!
 */