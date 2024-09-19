package com.springanything.aws;

import java.io.InputStream;
import java.util.List;
import jakarta.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class S3UploadController {

  private final S3Service s3Service;

  @PostMapping("/test/s3-file")
  public ResponseEntity<MultipartFileApiResponse> uploadFile(@Valid @ModelAttribute MultipartFileRequest request) {
    String url = s3Service.putObject(request.file());
    MultipartFileApiResponse apiResponse = new MultipartFileApiResponse(url);
    return ResponseEntity.ok(apiResponse);
  }

  @PostMapping("/test/s3-files")
  public ResponseEntity<List<MultipartFileApiResponse>> uploadFiles(@Valid @ModelAttribute MultipleMultipartFileRequest request) {
    List<MultipartFileApiResponse> apiResponses = s3Service.putObject(request.files())
      .stream()
      .map(MultipartFileApiResponse::new)
      .toList();
    return ResponseEntity.ok(apiResponses);
  }

  @GetMapping("/test/s3-file")
  public ResponseEntity<InputStreamResource> downloadFile(String filename) {
    InputStream fileStream = s3Service.downloadFile(filename);
    InputStreamResource resource = new InputStreamResource(fileStream);

    return ResponseEntity.ok()
      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
      .contentType(MediaType.APPLICATION_OCTET_STREAM)
      .body(resource);
  }
}
