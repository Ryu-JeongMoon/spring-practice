package com.springthymeleaf.typeconverter

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ConvertController {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping("/request-string")
  fun stringToInt(@RequestParam data: Int): Int {
    log.info("data = {}", data)
    return data
  }

  @GetMapping("/request-ip-port")
  fun stringToIpPort(@RequestParam ipPort: IpPort): IpPort {
    log.info("ipPort = {}", ipPort)
    return ipPort
  }
}