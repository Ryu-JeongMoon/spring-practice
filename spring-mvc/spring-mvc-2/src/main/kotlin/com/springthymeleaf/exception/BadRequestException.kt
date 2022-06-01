package com.springthymeleaf.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "잘못된 요청임둥")
class BadRequestException(message: String?) : RuntimeException(message) {
}