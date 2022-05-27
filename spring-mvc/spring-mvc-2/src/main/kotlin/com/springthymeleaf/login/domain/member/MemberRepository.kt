package com.springthymeleaf.login.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {

  fun findByLoginId(loginId: String): Optional<Member>
}