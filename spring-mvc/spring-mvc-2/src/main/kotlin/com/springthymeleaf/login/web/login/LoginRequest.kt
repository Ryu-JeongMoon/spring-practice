package com.springthymeleaf.login.web.login

import javax.validation.constraints.NotEmpty

data class LoginRequest(
  @field:NotEmpty
  var loginId: String?,
  @field:NotEmpty
  var password: String?
) {
  constructor() : this(null, null)
}