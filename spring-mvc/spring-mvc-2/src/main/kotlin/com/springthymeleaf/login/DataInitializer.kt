package com.springthymeleaf.login

import com.springthymeleaf.login.domain.member.Member
import com.springthymeleaf.login.domain.member.MemberRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DataInitializer(private val memberRepository: MemberRepository) {

  @PostConstruct
  fun init() {
    val member = Member(1L, "test", "tester", "1234")
    memberRepository.save(member)
  }
}