package com.springanything.aws;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.springanything.aws.AwsProperties.CloudFrontProperties;
import com.springanything.aws.AwsProperties.S3Properties;
import com.springanything.tsid.TsidUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3Service {

  private final S3Client s3Client;
  private final S3Properties s3Properties;
  private final CloudFrontProperties cloudFrontProperties;

  public String putObject(MultipartFile file) {
    String originalFilename = file.getOriginalFilename();
    String contentType = file.getContentType();
    String path = "doge";

    String filename = TsidUtils.generate(2);

    try {
      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
        .bucket(s3Properties.getBucket())
        .key(path + "/" + filename)
        .contentType(contentType)
        .contentLength(file.getSize())
        .build();

      PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
      if (!response.sdkHttpResponse().isSuccessful()) {
        throw new IllegalStateException("failed to upload file");
      }

      return String.format("https://%s/%s/%s", cloudFrontProperties.getDomain(), path, filename);

    } catch (IOException e) {
      log.error("failed to read file");
      throw new IllegalStateException(e);
    }
  }

  public Collection<String> putObject(Collection<MultipartFile> files) {
    return files.stream()
      .map(this::putObject)
      .toList();
  }

  public InputStream downloadFile(String filename) {
    try {
      GetObjectRequest objectRequest = GetObjectRequest.builder()
        .bucket(s3Properties.getBucket())
        .key("doge/" + filename)
        .build();
      return s3Client.getObject(objectRequest);
    } catch (S3Exception e) {
      log.error("failed to upload file");
      throw new IllegalStateException(e);
    }
  }

  public void deleteFile(String bucketName, String key) {
    DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
      .bucket(bucketName)
      .key(key)
      .build();

    s3Client.deleteObject(deleteObjectRequest);
  }
}
