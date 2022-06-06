package com.springthymeleaf.upload.controller

import com.springthymeleaf.upload.FileStore
import com.springthymeleaf.upload.domain.UploadItem
import com.springthymeleaf.upload.domain.UploadItemForm
import com.springthymeleaf.upload.domain.UploadItemRepository
import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.util.UriUtils
import java.nio.charset.StandardCharsets

@Controller
class UploadItemController(
  private val uploadItemRepository: UploadItemRepository,
  private val fileStore: FileStore
) {

  private val log = LoggerFactory.getLogger(javaClass)

  @GetMapping("/items/new")
  fun itemForm(@ModelAttribute form: UploadItemForm): String {
    return "file/item-form"
  }

  @PostMapping("/items/new")
  fun saveItem(@ModelAttribute form: UploadItemForm, redirectAttributes: RedirectAttributes): String {
    val attachFile = form.attachFile?.let { fileStore.storeFile(it).orElseThrow() }
    val storeImageFiles = form.imageFiles?.let { fileStore.storeFiles(it) }

    val uploadItem = storeImageFiles?.let { UploadItem(null, form.itemName, attachFile, it) }
    uploadItem?.let { uploadItemRepository.save(uploadItem) }

    val itemId = uploadItem?.id
    redirectAttributes.addAttribute("itemId", itemId)
    return "redirect:/items/$itemId"
  }

  @GetMapping("/items/{id}")
  fun items(@PathVariable id: Long, model: Model): String {
    val uploadItem = uploadItemRepository.findById(id).orElseThrow()
    model.addAttribute("item", uploadItem)
    return "file/item-view"
  }

  @ResponseBody
  @GetMapping("/images/{filename}")
  fun downloadImage(@PathVariable filename: String): Resource {
    return UrlResource("file:" + fileStore.getFullPath(filename))
  }

  @GetMapping("/attach/{itemId}")
  fun downloadAttach(@PathVariable itemId: Long): ResponseEntity<Resource> {
    val uploadItem = uploadItemRepository.findById(itemId).orElseThrow()
    val storeFileName = uploadItem.attachFile?.storeFileName
    val uploadFileName = uploadItem.attachFile?.uploadFileName

    val urlResource = UrlResource("file:" + storeFileName?.let { fileStore.getFullPath(it) })
    log.info("urlResource = {}", urlResource)

    val encodedUploadFilename = uploadFileName?.let { UriUtils.encode(it, StandardCharsets.UTF_8) }
    val contentDisposition = "attachment; filename=\"$encodedUploadFilename\""

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
      .body(urlResource)
  }
}

/*
content-disposition 헤더 주지 않으면 브라우저에서 열어버림
 */