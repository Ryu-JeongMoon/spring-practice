package com.springthymeleaf.login.web.member

import com.springthymeleaf.login.domain.member.Member
import com.springthymeleaf.login.domain.member.MemberRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/members")
class MemberController(
  private val memberRepository: MemberRepository
) {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping("/add")
  fun addForm(@ModelAttribute("member") member: Member): String {
    return "members/add-member-form"
  }

  @PostMapping("/add")
  fun add(@Validated @ModelAttribute member: Member, bindingResult: BindingResult): String {
    if (bindingResult.hasErrors())
      return "members/add-member-form"

    log.info("request member = {}", member)

    memberRepository.save(member)
    return "redirect:/"
  }
}