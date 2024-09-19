package com.springanything.aws;

import java.util.Collection;
import jakarta.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public record MultipleMultipartFileRequest(
  @NotEmpty
  Collection<MultipartFile> files
) {

}
