package com.springthymeleaf.upload

import com.springthymeleaf.upload.domain.UploadFile
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Component
class FileStore {

  @Value("\${file.directory}")
  private lateinit var directory: String

  fun getFullPath(filename: String): String {
    return directory + filename
  }

  fun storeFiles(multipartFiles: List<MultipartFile>): List<UploadFile> {
    return multipartFiles
      .filter { !it.isEmpty }
      .map { storeFile(it).orElseThrow() }
      .toCollection(mutableListOf())
  }

  fun storeFile(multipartFile: MultipartFile): Optional<UploadFile> {
    if (multipartFile.isEmpty)
      return Optional.empty()

    val originalFilename = multipartFile.originalFilename
    val storeFilename = originalFilename?.let { createStoreFilename(it) }

    storeFilename
      ?.let { getFullPath(it) }
      ?.let { File(it) }
      ?.let { multipartFile.transferTo(it) }

    return Optional.ofNullable(UploadFile(null, originalFilename, storeFilename))
  }

  private fun createStoreFilename(originalFilename: String): String {
    val extension: String = extractExtension(originalFilename)
    val uuid = UUID.randomUUID().toString()
    return "$uuid.$extension"
  }

  private fun extractExtension(originalFilename: String): String {
    val indexOfDot = originalFilename.lastIndexOf(".")
    return originalFilename.substring(indexOfDot + 1)
  }
}