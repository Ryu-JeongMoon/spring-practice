package com.springanything.mvc;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springanything.mapping.TestRequest;

@RestController
public class RestViewController {

	@GetMapping("/test/rest-view")
	public ModelAndView view(Model model) {
		TestRequest testRequest = TestRequest.of("", 0, new TestRequest.TestInnerRequest(""));
		model.addAttribute("testRequest", testRequest);
		return new ModelAndView("test-view");
	}
}

/*
https://stackoverflow.com/questions/33062509/returning-view-from-spring-mvc-restcontroller
ModelAndView를 반환함으로써 RestController에서 view 반환할 수는 있지만 절대 권장되는 방식이 아니다
되는데 왜 쓰지 말라고 하는 것인가?

1. 차라리 Controller를 사용하고 @ResponseBody를 개별로 다 붙이거나
2. Controller, RestController 분리하는 것이 낫다
 */