package com.springanything.aws;

import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public record MultipartFileRequest(
  @NotNull
  MultipartFile file
) {

}
