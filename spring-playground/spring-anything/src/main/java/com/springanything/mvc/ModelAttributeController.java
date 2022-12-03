package com.springanything.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springanything.mapping.TestRequest;

@Controller
public class ModelAttributeController {

	/**
	 * ModelAttribute 매핑은 Model에 별도로 추가하지 않아도 자동으로 추가된다<br/>
	 * Repository에서 반환 받은 값을 Model로 보내려면<br/>
	 * 기존의 @ModelAttribute로 매핑 받는 객체에 재할당을 할 순 없고 상태의 변경이 필요하다<br/>
	 * 이 때 사용하기 위한 API<br/>
	 * <pre>{@code vo = repository.get(vo) 불가
	 * vo.set(repository.get(vo)) 가능}</pre>
	 */
	@GetMapping("/model-attribute")
	public String autoMapping(@ModelAttribute TestRequest request) {
		// request = getTestRequest(request); -> 불가, 값 매핑되지 않음

		TestRequest result = getTestRequest(request);
		// request.setTestInnerRequest(result.getTestInnerRequest());
		return "test-view";
	}

	private TestRequest getTestRequest(TestRequest request) {
		return new TestRequest(request.getName(), request.getAge(), new TestRequest.TestInnerRequest("yaho!!"));
	}

}

