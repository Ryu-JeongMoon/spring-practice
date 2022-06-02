package com.springthymeleaf.typeconverter

import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.format.annotation.NumberFormat
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime

@Controller
class FormatController {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping("/formatter-edit")
  fun editForm(model: Model): String {
    val data = Data(10000, LocalDateTime.now())
    log.info("data = {}", data)
    model.addAttribute(data)
    return "formatter-form"
  }

  @PostMapping("/formatter-edit")
  fun edit(@ModelAttribute data: Data, model: Model): String {
    log.info("data = {}", data)
    model.addAttribute("data", data)
    return "formatter-view"
  }
}

data class Data(
  @field:NumberFormat(pattern = "###,###")
  var number: Int?,

  @field:DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  var datetime: LocalDateTime?
)