package com.springanything.mapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springanything.junit.PandaBear;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MappingController {

	@GetMapping("/no-args-mapping")
	public String view(@ModelAttribute PandaBear pandaBear) {
		return "mapping-view";
	}

	@ResponseBody
	@PostMapping("/no-args-mapping")
	public PandaBear save(@ModelAttribute PandaBear pandaBear) {
		log.info("pandaBear = {}", pandaBear);
		return pandaBear;
	}
}

/*
Setter 없는 모델, view에서 폼으로 전송된 데이터 @NoArgs로 매핑 가능한가?
@NoArgsConstructor(access = AccessLevel.PROTECTED) 기본 생성자 접근 제한 해둘 때는 가능

@ModelAttribute 써서 자동으로 넣어주면 view에서 마크업 에러 발생, 작동에는 문제 없음
model.addAttribute("pandaBear", new PandaBear()); 로 해줘야 view에서 마크업 에러 발생하지 않음
버전 업그레이드 되면서 해결될 문제로 보임
 */