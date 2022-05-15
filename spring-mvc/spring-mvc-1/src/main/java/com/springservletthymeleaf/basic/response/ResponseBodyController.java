package com.springservletthymeleaf.basic.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springservletthymeleaf.basic.BasicData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ResponseBodyController {

	/* 가장 기초적인 방식 */
	@GetMapping("/response-body-string-v1")
	public void responseBodyV1(HttpServletResponse response) throws IOException {
		response.getWriter().write("v1");
	}

	/* ResponseEntity로 응답 코드까지 반환 가능, 나아가서 HATEOAS도 적용 가능 */
	@GetMapping("/response-body-string-v2")
	public ResponseEntity<String> responseBodyV2() {
		return new ResponseEntity<>("v2", HttpStatus.OK);
	}

	@GetMapping("/response-body-string-v3")
	public String responseBodyV3() {
		return "v3";
	}

	@GetMapping("/response-body-json-v1")
	public ResponseEntity<BasicData> responseBodyJsonV1() {
		BasicData data = new BasicData("json-v1", 20);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/response-body-json-v2")
	public BasicData responseBodyJsonV2() {
		return new BasicData("json-v2", 20);
	}
}