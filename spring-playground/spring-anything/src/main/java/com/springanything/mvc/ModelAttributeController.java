package com.springanything.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springanything.mapping.TestRequest;

@Controller
public class ModelAttributeController {

	/**
	 * ModelAttribute로 받아올 시 별도로 Model에 담아줄 필요 없음<br/>
	 * html 보낼 때 자동으로 담아서 보내준다
	 */
	@GetMapping("/model-attribute")
	public String autoMapping(@ModelAttribute TestRequest request) {
		request.setTestInnerRequest(new TestRequest.TestInnerRequest("yahoo~!~!~!"));
		return "test-view";
	}

}

