package com.springservletthymeleaf.basic.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestBodyStringController {

	@PostMapping("/request-body-string-v1")
	public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		String message = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		log.info("message={}", message);
		response.getWriter().write("ok");
	}

	/* InputStream, Writer 직접 받아올 수 있다 */
	@PostMapping("/request-body-string-v2")
	public void requestBodyString(InputStream inputStream, Writer writer) throws IOException {
		String message = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		log.info("message={}", message);
		writer.write("ok");
	}

	/* HttpHeader, HttpBody를 갖는 컨테이너 HttpEntity를 직접 받아올 수 있다 */
	@PostMapping("/request-body-string-v3")
	public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) {
		String message = httpEntity.getBody();
		log.info("message={}", message);
		return new HttpEntity<>("ok");
	}

	@PostMapping("/request-body-string-v3.1")
	public ResponseEntity<String> requestBodyStringV3_1(RequestEntity<String> requestEntity) {
		URI url = requestEntity.getUrl();
		String message = requestEntity.getBody();
		HttpMethod method = requestEntity.getMethod();
		HttpHeaders headers = requestEntity.getHeaders();

		log.info("url = {}", url);
		log.info("method = {}", method);
		log.info("message = {}", message);
		log.info("headers = {}", headers);

		return new ResponseEntity<>("ok", HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping("/request-body-string-v4")
	public String requestBodyStringV4(@RequestBody String messageBody) {
		log.info("messageBody={}", messageBody);
		return "ok";
	}
}
