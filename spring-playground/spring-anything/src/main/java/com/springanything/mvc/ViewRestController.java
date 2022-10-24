package com.springanything.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springanything.mapping.TestRequest;

@RestController
public class ViewRestController {

	@GetMapping("/view-in-rest")
	public ModelAndView view() {
		ModelAndView modelAndView = new ModelAndView("test-view");
		modelAndView.addObject("testRequest", new TestRequest("panda", 255, new TestRequest.TestInnerRequest("yaho!!")));
		return modelAndView;
	}
}

/*
RestController 안에서 뷰 반환하고 싶다면 ModelAndView 사용
 */