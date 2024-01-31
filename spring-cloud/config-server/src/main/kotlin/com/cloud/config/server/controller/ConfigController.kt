package com.cloud.config.server.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigController {

  private val log: Logger = LoggerFactory.getLogger(javaClass)

  @PostMapping("/config/{app}/{profile}/{key}")
  fun getCentralConfig(
    @PathVariable("app") app: String?,
    @PathVariable("profile") profile: String?,
    @PathVariable("key") key: String?,
    @RequestBody value: String?
  ): String {
    log.info("app >>> {}", app)
    log.info("profile >>> {}", profile)
    log.info("key >>> {}", key)
    log.info("value >>> {}", value)
    return "OK"
  }
}
