package com.springthymeleaf.basic

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/template")
class TemplateController {

  @GetMapping("/fragment")
  fun template(model: Model): String {
    return "template/fragment/fragmentMain"
  }

  @GetMapping("/layout")
  fun layout(model: Model): String {
    return "template/layout/layoutMain"
  }

  @GetMapping("/layoutExtend")
  fun layoutExtends(): String {
    return "template/layoutExtend/layoutExtendMain"
  }
}