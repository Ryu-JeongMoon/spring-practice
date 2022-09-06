package com.springanything.mapping;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MappingTestController {

	@GetMapping("/test-view")
	public String testView(Model model) {
		TestRequest testRequest = TestRequest.of("", 0, new TestRequest.TestInnerRequest(""));
		model.addAttribute("testRequest", testRequest);
		return "test-view";
	}

	@ResponseBody
	@PostMapping(
		value = "/test/request-body",
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
	public TestRequest testRequestBody(@RequestBody TestRequest testRequest) {
		log.info("testRequest = {}", testRequest);
		return testRequest;
	}

	@ResponseBody
	@PostMapping(
		value = "/test/model-attribute",
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
	)
	public TestRequest testModelAttribute(@ModelAttribute TestRequest testRequest) {
		log.info("testRequest = {}", testRequest);
		return testRequest;
	}

	@ResponseBody
	@GetMapping(value = "/test/nested")
	public NestedRequest reiterate(@ModelAttribute NestedRequest request) {
		log.info("request = {}", request);
		return request;
	}
}
