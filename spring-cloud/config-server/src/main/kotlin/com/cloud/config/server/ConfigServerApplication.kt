package com.cloud.config.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer

@EnableConfigServer
@SpringBootApplication
class ConfigServerApplication

fun main(args: Array<String>) {
  SpringApplication.run(ConfigServerApplication::class.java, *args)
}
