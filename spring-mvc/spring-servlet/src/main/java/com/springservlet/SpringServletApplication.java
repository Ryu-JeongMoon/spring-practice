package com.springservlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SpringServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringServletApplication.class, args);
	}

}
