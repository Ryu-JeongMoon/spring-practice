package com.springthymeleaf.typeconverter.converter

import com.springthymeleaf.typeconverter.IpPort
import org.slf4j.LoggerFactory
import org.springframework.core.convert.converter.Converter


class IpPortToStringConverter : Converter<IpPort, String> {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun convert(source: IpPort): String {
    log.info("source = {}", source)
    return "${source.ip}:${source.port}"
  }
}

class StringToIpPortConverter : Converter<String, IpPort> {

  private val log = LoggerFactory.getLogger(javaClass)

  override fun convert(source: String): IpPort {
    log.info("source = {}", source)
    val strings = source.split(":")
    return IpPort(strings[0], strings[1].toInt())
  }
}