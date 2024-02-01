package com.cloud.config.client

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@SpringBootApplication
@ConfigurationPropertiesScan
class ConfigClientApplication

fun main(args: Array<String>) {
  SpringApplication.run(ConfigClientApplication::class.java, *args)
}
