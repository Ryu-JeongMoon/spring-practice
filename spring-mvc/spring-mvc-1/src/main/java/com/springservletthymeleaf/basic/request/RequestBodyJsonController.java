package com.springservletthymeleaf.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springservletthymeleaf.basic.BasicData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestBodyJsonController {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@PostMapping("/request-body-json-v1")
	public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ServletInputStream inputStream = request.getInputStream();
		String message = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

		log.info("message={}", message);
		BasicData data = objectMapper.readValue(message, BasicData.class);
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		response.getWriter().write("ok");
	}

	@ResponseBody
	@PostMapping("/request-body-json-v2")
	public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {
		BasicData data = objectMapper.readValue(messageBody, BasicData.class);
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		return "ok";
	}

	@ResponseBody
	@PostMapping("/request-body-json-v3")
	public String requestBodyJsonV3(@RequestBody BasicData data) {
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		return "ok";
	}

	@ResponseBody
	@PostMapping("/request-body-json-v4")
	public String requestBodyJsonV4(HttpEntity<BasicData> httpEntity) {
		BasicData data = httpEntity.getBody();
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		return "ok";
	}

	@ResponseBody
	@PostMapping("/request-body-json-v5")
	public BasicData requestBodyJsonV5(@RequestBody BasicData data) {
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		return data;
	}
}