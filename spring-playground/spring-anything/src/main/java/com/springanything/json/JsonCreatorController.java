package com.springanything.json;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonCreatorController {

	@GetMapping("/test/json-creator")
	public JsonCreatorResponse getJsonCreatorResponse(JsonCreatorResponse jsonCreatorResponse) {
		return jsonCreatorResponse;
	}

	@PostMapping("/test/json-creator")
	public JsonCreatorResponse postJsonCreatorResponse(@RequestBody JsonCreatorResponse jsonCreatorResponse) {
		return jsonCreatorResponse;
	}
}
