package com.springthymeleaf.login.web

import com.springthymeleaf.login.domain.member.MemberRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(private val memberRepository: MemberRepository) {

  //  @GetMapping("/")
  fun home(): String {
    return "home"
  }

  @GetMapping("/")
  fun loginHome(@CookieValue(value = "memberId", required = false) id: Long?, model: Model): String {
    id ?: return "home"

    val member = memberRepository.findById(id).orElse(null)
    member ?: return "home"

    model.addAttribute("member", member)
    return "login/login-home"
  }
}

/*
@CookieValue로 쿠키 바로 뽑아낼 수 있당
 */