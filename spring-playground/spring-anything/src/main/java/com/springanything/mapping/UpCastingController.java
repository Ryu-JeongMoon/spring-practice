package com.springanything.mapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpCastingController {

	@GetMapping("/test/up-casting")
	public SuperTestRequest upcasting() {
		return TestRequest.of("panda", 155, null);
	}

	@PostMapping("/test/snake_case")
	public SuperTestRequest snakeCase(@RequestBody TestRequest testRequest) {
		return testRequest;
	}

}
