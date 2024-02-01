package com.cloud.config.client.controller

import com.cloud.config.client.config.CentralPropertiesWrapper
import org.slf4j.LoggerFactory
import org.springframework.cloud.context.refresh.ContextRefresher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController(
  private val configDataContextRefresher: ContextRefresher,
  private val centralPropertiesWrapper: CentralPropertiesWrapper
) {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping("/central-properties")
  fun getCentralConfig(
    @RequestParam(value = "refresh", required = false) refresh: Boolean
  ): CentralPropertiesWrapper {
    if (refresh) {
      configDataContextRefresher.refresh()
    }

    log.info("centralProperties >> {}", centralPropertiesWrapper)
    log.info("centralProperties.hashCode >> {}", System.identityHashCode(centralPropertiesWrapper))
    return centralPropertiesWrapper
  }
}
