package com.springthymeleaf.login.domain.login

import com.springthymeleaf.login.domain.member.Member
import com.springthymeleaf.login.domain.member.MemberRepository
import org.springframework.stereotype.Service

@Service
class LoginService(private val memberRepository: MemberRepository) {

  fun login(member: Member): Member? {
    return memberRepository.findByLoginId(member.loginId!!)
      .filter { findMember -> member.password.equals(findMember.password) }
      .orElse(null)
  }
}