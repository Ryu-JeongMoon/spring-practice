package com.springthymeleaf.login.web.session

import com.springthymeleaf.login.domain.member.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse

internal class SessionManagerTest {

  private val sessionManager = SessionManager()
  private var response = MockHttpServletResponse()
  private var request = MockHttpServletRequest()
  private lateinit var member: Member

  @BeforeEach
  fun init() {
    member = Member(1L, "panda", "bear", "1234")
    response = MockHttpServletResponse()
    sessionManager.create(member, response)
    request.setCookies(*response.cookies)
  }

  @Test
  fun `세션 생성`() {
    // given
    val member = Member(2L, "bear", "panda", "5678")
    val response = MockHttpServletResponse()
    val request = MockHttpServletRequest()

    // when
    sessionManager.create(member, response)
    request.setCookies(*response.cookies)

    // then
    val sessionValue = sessionManager.get(request)
    assertThat(sessionValue).isNotNull
  }

  @Test
  fun `세션 조회`() {
    // when
    val sessionValue = sessionManager.get(request)

    // then
    assertThat(sessionValue).isEqualTo(member)
  }

  @Test
  fun `세션 만료`() {
    // when
    sessionManager.expire(request)

    // then
    val sessionValue = sessionManager.get(request)
    assertThat(sessionValue).isNull()
  }
}