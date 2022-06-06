package com.springthymeleaf.upload.controller

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.io.InputStream
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/servlet/v2")
class ServletUploadControllerV2 {

  @Value("\${file.directory}")
  private lateinit var directory: String

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping("/upload")
  fun newFileForm(): String {
    return "file/upload-form"
  }

  @PostMapping("/upload")
  fun saveFileV1(request: HttpServletRequest): String {
    log.info("request = {}", request)

    val itemName = request.getParameter("itemName")
    log.info("itemName = {}", itemName)

    val parts = request.parts
    log.info("parts = {}", parts)

    for (part in parts) {
      log.info("==== PART ====")
      log.info("name={}", part.name)

      val headerNames = part.headerNames
      for (headerName in headerNames) {
        log.info(
          "header {}: {}", headerName,
          part.getHeader(headerName)
        )
      }

      val inputStream: InputStream = part.inputStream
//      val body: String = StreamUtils.copyToString(
//        inputStream,
//        StandardCharsets.UTF_8
//      )
//      log.info("body={}", body)

      if (StringUtils.hasText(part.submittedFileName)) {
        val fullPath: String = directory + part.submittedFileName
        log.info("파일 저장 fullPath={}", fullPath)
        part.write(fullPath)
      }
    }

    return "file/upload-form"
  }
}

/*
Part가 제공하는 write 편의 메서드로 간편하게 저장이 가능하지만
HttpServletRequest에서 직접 뽑아내야 한다는 것이 불편
 */