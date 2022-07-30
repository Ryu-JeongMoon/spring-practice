package com.springanything.mapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpCastingController {

	@GetMapping("/test/up-casting")
	public SuperTestRequest upcasting() {
		return TestRequest.of("panda", 155, null);
	}

}
