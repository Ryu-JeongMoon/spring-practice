package com.springanything.json;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonUnwrappedController {

	@GetMapping("/test-json-unwrapped")
	public JsonBigResponse testJsonUnwrapped() {
		return new JsonBigResponse(
			new JsonMediumResponse("PANDA", "nickname"), new JsonSmallResponse("small")
		);
	}

	@GetMapping("/test-json-unwrapped-mapping")
	public JsonRequest testJsonUnwrapped(@ModelAttribute JsonRequest request) {
		return request;
	}

	@GetMapping("/test-json-boolean-to-int")
	public JsonBooleanRequest testJsonBoolean(@ModelAttribute JsonBooleanRequest request) {
		return request;
	}
}
