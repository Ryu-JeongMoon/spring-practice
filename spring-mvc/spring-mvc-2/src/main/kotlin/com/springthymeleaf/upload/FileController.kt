package com.springthymeleaf.upload

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class FileController {

  @GetMapping("/file")
  fun fileHome(): String {
    return "file-index"
  }
}