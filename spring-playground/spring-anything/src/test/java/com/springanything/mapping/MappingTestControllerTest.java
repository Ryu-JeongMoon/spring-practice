package com.springanything.mapping;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

@WebMvcTest(controllers = MappingTestController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MappingTestControllerTest {

	@Autowired
	WebTestClient webTestClient;

	@Test
	@DisplayName("JSON - Request Body")
	void jsonRequestBody() throws Exception {
		// given
		TestRequest vo = TestRequest.of("test-name", 100, new TestRequest.TestInnerRequest("test-inner-name"));

		// when
		EntityExchangeResult<byte[]> result = webTestClient.post()
			.uri("/test/request-body")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(vo)
			.exchange()
			.expectBody()
			.returnResult();

		// then
		MockMvcWebTestClient.resultActionsFor(result)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("XML - Request Body")
	void xmlRequestBody() throws Exception {
		// given
		TestRequest vo = TestRequest.of("test-name", 100, new TestRequest.TestInnerRequest("test-inner-name"));

		// when
		EntityExchangeResult<byte[]> result = webTestClient.post()
			.uri("/test/request-body")
			.contentType(MediaType.APPLICATION_XML)
			.accept(MediaType.APPLICATION_XML)
			.bodyValue(vo)
			.exchange()
			.expectBody()
			.returnResult();

		// then
		MockMvcWebTestClient.resultActionsFor(result)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("FORM-URL-ENCODED - Model Attribute")
	void formUrlEncodedModelAttribute() throws Exception {
		// given
		TestRequest vo = TestRequest.of("test-name", 100, new TestRequest.TestInnerRequest("test-inner-name"));
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("name", vo.getName());
		formData.add("age", String.valueOf(vo.getAge()));
		formData.add("testInnerRequest", vo.getTestInnerRequest().getInnerName());

		// when
		EntityExchangeResult<byte[]> result = webTestClient.post()
			.uri("/test/model-attribute")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(BodyInserters.fromFormData(formData))
			.exchange()
			.expectBody()
			.returnResult();

		// then
		MockMvcWebTestClient.resultActionsFor(result)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("FORM-DATA - Model Attribute")
	void formDataModelAttribute() throws Exception {
		// given
		TestRequest vo = TestRequest.of("test-name", 100, new TestRequest.TestInnerRequest("test-inner-name"));
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("name", vo.getName());
		formData.add("age", String.valueOf(vo.getAge()));
		formData.add("testInnerRequest", vo.getTestInnerRequest().getInnerName());

		// when
		EntityExchangeResult<byte[]> result = webTestClient.post()
			.uri("/test/model-attribute")
			.contentType(MediaType.MULTIPART_FORM_DATA)
			.bodyValue(formData)
			.exchange()
			.expectBody()
			.returnResult();

		// then
		MockMvcWebTestClient.resultActionsFor(result)
			.andDo(print())
			.andExpect(status().isOk());
	}

}