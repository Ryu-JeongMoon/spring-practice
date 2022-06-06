package com.springthymeleaf.upload.domain

import org.springframework.data.jpa.repository.JpaRepository

interface UploadItemRepository : JpaRepository<UploadItem, Long> {
}