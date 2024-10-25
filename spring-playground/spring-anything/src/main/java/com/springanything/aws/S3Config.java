package com.springanything.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springanything.aws.AwsProperties.S3Properties;

import lombok.RequiredArgsConstructor;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class S3Config {

  private final S3Properties s3Properties;

  @Bean
  public S3Client s3Client() {
    AwsBasicCredentials credentials = AwsBasicCredentials.create(
      s3Properties.getAccessKey(),
      s3Properties.getSecretKey()
    );

    return S3Client.builder()
      .region(Region.of(s3Properties.getRegion()))
      .credentialsProvider(StaticCredentialsProvider.create(credentials))
      .build();
  }
}