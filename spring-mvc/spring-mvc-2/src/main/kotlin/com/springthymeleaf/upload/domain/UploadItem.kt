package com.springthymeleaf.upload.domain

import javax.persistence.*

@Entity
class UploadItem(
  @Id @GeneratedValue
  var id: Long?,
  var itemName: String?,
  @OneToOne
  var attachFile: UploadFile?,
  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "upload_file_id")
  var imageFiles: List<UploadFile> = mutableListOf()
) {

  constructor() : this(null, null, null, arrayListOf())
}