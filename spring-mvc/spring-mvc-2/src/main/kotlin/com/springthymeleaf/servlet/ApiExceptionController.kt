package com.springthymeleaf.servlet

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiExceptionController {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping("/api/members/{id}")
  fun getMember(@PathVariable id: String): MemberResponse {
    if (id == "ex")
      throw RuntimeException("오노")
    if (id == "bad")
      throw IllegalArgumentException("오우노")

    return MemberResponse(id, "panda-$id")
  }
}

data class MemberResponse(
  var id: String?,
  var name: String?
)