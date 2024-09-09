package com.springanything.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "app.mail")
@Data
public class CustomMailProperties {

  private String address;
  private String personal;
}
