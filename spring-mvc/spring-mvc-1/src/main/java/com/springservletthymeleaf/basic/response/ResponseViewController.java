package com.springservletthymeleaf.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

	@RequestMapping("/response-view-v1")
	public ModelAndView responseViewV1() {
		return new ModelAndView("response/hello").addObject("data", "v1!!");
	}

	@RequestMapping("/response-view-v2")
	public String responseViewV2(Model model) {
		model.addAttribute("data", "v2!!");
		return "response/hello";
	}

	@RequestMapping("/response/hello")
	public void responseViewV3(Model model) {
		model.addAttribute("data", "v3!!");
	}
}

/*
v3
return type -> void 면서 HttpServletResponse, OutputStream 같이
응답을 처리할 parameter 받지 않는 경우에 매핑되는 url 을 뷰 이름으로 바꿔 반환해준다
명시적으로 반환해주지 않기 때문에 추천하지 않는 방식, v2가 적당
 */