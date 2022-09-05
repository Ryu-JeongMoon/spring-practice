package com.springanything.mapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RequestParamTestController {

	@GetMapping("/test/request-param")
	public TestRequest mapping(@ModelAttribute TestRequest testRequest) {
		log.info("testRequest = {}", testRequest);
		return testRequest;
	}

	@GetMapping("/test/request-param/{name}")
	public TestRequest mappingPathVariable(@ModelAttribute TestRequest testRequest) {
		log.info("testRequest = {}", testRequest);
		return testRequest;
	}

	@PostMapping("/test/request-body/{name}")
	public TestRequest mappingPathVariableIntoRequestBody(@RequestBody TestRequest testRequest) {
		log.info("testRequest = {}", testRequest);
		return testRequest;
	}
}
