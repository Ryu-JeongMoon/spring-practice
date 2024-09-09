package com.springanything;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringAnythingApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringAnythingApplication.class, args);
  }
}
