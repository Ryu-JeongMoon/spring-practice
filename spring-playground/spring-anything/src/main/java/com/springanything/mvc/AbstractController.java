package com.springanything.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public abstract class AbstractController {

	/**
	 * <pre>
	 * &#064;RequestMapping 등의 매핑 어노테이션을 사용하면
	 * spring-mvc는 가시성을 체크하지 않고 Reflection API를 사용해 호출한다.
	 * 따라서 private 메서드도 호출할 수 있다.
	 * 뼈대 클래스에 만들어 둔 후 호출해도 가능 ?!
	 * <a href="https://rules.sonarsource.com/java/RSPEC-3751">Reference</a></pre>
	 */
	@GetMapping("/test/abstract")
	private String privateAndAbstractMapping() {
		return "hello world";
	}
}

/*
200 OK
뼈대 클래스에서 Mapping 잡아놓으면 구현 클래스에서 재정의하지 않아도 사용 가능
 */