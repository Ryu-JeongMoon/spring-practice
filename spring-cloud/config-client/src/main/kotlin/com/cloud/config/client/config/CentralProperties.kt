package com.cloud.config.client.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope

@RefreshScope
@ConfigurationProperties(prefix = "central")
class CentralProperties {

  var test: String = ""
  var test1: String = ""
  var test2: String = ""

  override fun toString(): String {
    return "CentralProperties(test='$test', test1='$test1', test2='$test2')"
  }
}
