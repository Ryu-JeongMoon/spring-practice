package com.springanything.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springanything.mapping.TestRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ModelAttributeController {

  /**
   * <pre>
   * ModelAttribute 매핑은 Model에 별도로 추가하지 않아도 자동으로 추가된다
   * Repository에서 반환 받은 값을 Model로 보내려면
   * 기존의 @ModelAttribute로 매핑 받는 객체에 재할당을 할 순 없고 상태의 변경이 필요하다
   * 이 때 사용하기 위한 API
   *
   * {@code
   * vo = repository.get(vo) 불가
   * vo.set(repository.get(vo)) 가능
   * }</pre>
   */
  @GetMapping("/model-attribute")
  public String autoMapping(@ModelAttribute TestRequest testRequest) {
    // testRequest = getTestRequest(testRequest); -> 불가, 값 매핑되지 않음

    log.info("testRequest: {}", testRequest);

    // TestRequest result = getTestRequest(testRequest);
    // log.info("result: {}", result);

    // testRequest.setTestInnerRequest(result.getTestInnerRequest());
    return "test-view";
  }

  private TestRequest getTestRequest(TestRequest request) {
    return new TestRequest(request.getName(), request.getAge(), new TestRequest.TestInnerRequest("yaho!!"));
  }
}

