package com.springanything.mvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/concrete")
public class ConcreteController extends AbstractController {

	// /concrete/test/abstract ok~!
	// 구체 클래스에서 지정해둔 Mapping이 먼저 적용
}
