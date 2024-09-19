package com.springanything.aws;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "integration.aws")
@Data
public class AwsProperties {

  @ConfigurationProperties(prefix = "integration.aws.s3")
  @Data
  public static class S3Properties {

    private String region;
    private String bucket;
    private String accessKey;
    private String secretKey;
  }

  @ConfigurationProperties(prefix = "integration.aws.cloudfront")
  @Data
  public static class CloudFrontProperties {

    private String domain;
  }
}
