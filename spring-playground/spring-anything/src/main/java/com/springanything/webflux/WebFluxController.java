package com.springanything.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springanything.mapping.TestRequest;

import reactor.core.publisher.Flux;

@RestController
public class WebFluxController {

	@GetMapping("/test/flux")
	public Flux<TestRequest> shoot() {
		return Flux.just(TestRequest.of("panda", 155, new TestRequest.TestInnerRequest("bear")));
	}

}
