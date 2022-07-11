package com.springanything.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WithParameter
@RestController
public class AopController {

	@GetMapping("/test/aop-model-attribute")
	public AopRequest mappingModelAttribute(AopRequest aopRequest) {
		log.info("aopRequest = {}", aopRequest);
		return aopRequest;
	}

	@GetMapping("/test/aop-request-body")
	public AopRequest mappingRequestBody(@RequestBody AopRequest aopRequest) {
		log.info("aopRequest = {}", aopRequest);
		return aopRequest;
	}
}
