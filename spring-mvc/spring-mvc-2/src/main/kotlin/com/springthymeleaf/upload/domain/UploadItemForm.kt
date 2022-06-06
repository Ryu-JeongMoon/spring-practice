package com.springthymeleaf.upload.domain

import org.springframework.web.multipart.MultipartFile

data class UploadItemForm(
  var itemId: Long?,
  var itemName: String?,
  var imageFiles: List<MultipartFile>?,
  var attachFile: MultipartFile?
) {
}