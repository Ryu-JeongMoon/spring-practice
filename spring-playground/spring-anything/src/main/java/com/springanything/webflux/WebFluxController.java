package com.springanything.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springanything.mapping.TestRequest;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
public class WebFluxController {

	@GetMapping("/test/flux")
	public Flux<TestRequest> shoot() {
		return Flux.just(TestRequest.of("panda", 155, new TestRequest.TestInnerRequest("bear")));
	}

	@GetMapping("/test/count")
	public int returnCount() {
		return 800;
	}

	@PostMapping("/test/agents")
	public WebFluxRequest shoot2(@RequestBody WebFluxRequest webFluxRequest) throws InterruptedException {
		log.info("webFluxRequest: {}", webFluxRequest);
		Thread.sleep(3000);
		return webFluxRequest;
	}
}
