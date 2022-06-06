package com.springthymeleaf.upload.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class UploadFile(
  @Id @GeneratedValue
  @Column(name = "upload_file_id")
  var id: Long?,
  var uploadFileName: String?,
  var storeFileName: String?,
) {

}
