package com.springthymeleaf.exception.controller

import com.springthymeleaf.exception.BadRequestException
import com.springthymeleaf.exception.CustomException
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiExceptionController {

  @GetMapping("/api/members/{id}")
  fun getMember(@PathVariable id: String): MemberResponse {
    if (id == "ex")
      throw RuntimeException("오노")
    if (id == "bad")
      throw IllegalArgumentException("오우노")
    if (id == "custom")
      throw CustomException("오우노우")

    return MemberResponse(id, "panda-$id")
  }


  @GetMapping("/response-status")
  fun handleResponseStatus(): String {
    throw BadRequestException("yahoo!")
  }

  @GetMapping("/api/default-exception-handler", produces = [MediaType.APPLICATION_JSON_VALUE])
  fun handleDefaultException(@RequestParam data: Int): String {
    return data.toString()
  }

}

data class MemberResponse(
  var id: String?,
  var name: String?
)