package com.springthymeleaf.upload.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.util.StreamUtils
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.InputStream
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/spring")
class SpringUploadControllerV2 {

  @Value("\${file.directory}")
  private lateinit var directory: String

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping("/upload")
  fun newFileForm(): String {
    return "file/upload-form"
  }

  @PostMapping("/upload")
  fun saveFileV1(
    @RequestParam itemName: String,
    @RequestParam file: MultipartFile,
    request: HttpServletRequest
  ): String {

    log.info("request = {}", request)
    log.info("itemName = {}", request.getParameter("itemName"))

    if (!file.isEmpty) {
      val fullPath = directory + file.originalFilename
      file.transferTo(File(fullPath))
    }

    return "file/upload-form"
  }
}

/*
MultipartFile로 바로 받아버려 더 간단하게 사용 가능하다
Kotlin에서는 I/O Exception도 안 잡아줘도 되서 더 깰끔
 */