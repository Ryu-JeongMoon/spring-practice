package com.springservletthymeleaf.basic.request;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springservletthymeleaf.basic.BasicData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestParamController {

	@RequestMapping("/request-param-v1")
	public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		int age = Integer.parseInt(request.getParameter("age"));

		log.info("username={}, age={}", username, age);
		response.getWriter().write("ok");
	}

	/* @RequestParam를 이용하면 request.getParameter("어쩌구") 같은 동일한 작업을 하지 않아도 된다 */
	@ResponseBody
	@RequestMapping("/request-param-v2")
	public String requestParamV2(
		@RequestParam("username") String memberName,
		@RequestParam("age") int memberAge) {

		log.info("username={}, age={}", memberName, memberAge);
		return "ok";
	}

	/* parameter 이름을 입력 받는 값과 동일하게 맞춰주면 @RequestParam의 value 옵션 주지 않아도 된다 */
	@ResponseBody
	@RequestMapping("/request-param-v3")
	public String requestParamV3(
		@RequestParam String username,
		@RequestParam int age) {
		log.info("username={}, age={}", username, age);
		return "ok";
	}

	/* @RequestParam도 빼버릴 수 있다 */
	@ResponseBody
	@RequestMapping("/request-param-v4")
	public String requestParamV4(String username, int age) {
		log.info("username={}, age={}", username, age);
		return "ok";
	}

	/* 필수 값 여부를 설정할 수 있고 안 들어오면 BadRequest 터트림 */
	@ResponseBody
	@RequestMapping("/request-param-required")
	public String requestParamRequired(
		@RequestParam(required = true) String username,
		@RequestParam(required = false) Integer age) {

		log.info("username={}, age={}", username, age);
		return "ok";
	}

	/* defaultValue 옵션 주면 없으면 기본 값 들어가므로 required 옵션은 사용할 필요가 없다 */
	@ResponseBody
	@RequestMapping("/request-param-default")
	public String requestParamDefault(
		@RequestParam(defaultValue = "guest") String username,
		@RequestParam(defaultValue = "-1") int age) {

		log.info("username={}, age={}", username, age);
		return "ok";
	}

	/* Map은 하나의 parameter, 하나의 값만 매핑 & MultiValueMap은 하나의 parameter, 여러 값 매핑 */
	@ResponseBody
	@RequestMapping("/request-param-map")
	public String requestParamMap(@RequestParam MultiValueMap<String, Object> paramMap) {

		log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
		return "ok";
	}

	@ResponseBody
	@RequestMapping("/model-attribute-v1")
	public String modelAttributeV1(@ModelAttribute BasicData basicData) {
		log.info("basicData = {}", basicData);
		return "ok";
	}

	/* 객체 타입의 경우 @RequestParam, @ModelAttribute 안 붙여놓으면 기본으로 @ModelAttribute 붙는다 */
	@ResponseBody
	@RequestMapping("/model-attribute-v2")
	public String modelAttributeV2(BasicData basicData) {
		log.info("basicData = {}", basicData);
		return "ok";
	}

}

/*
@RequestParam -> primitive or boxed
@ModelAttribute -> reference
컨트롤러 메서드 매개변수에서 생략하면 기본으로 붙는다
 */