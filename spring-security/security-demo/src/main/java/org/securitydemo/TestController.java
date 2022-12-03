package org.securitydemo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TestController {

	@PostMapping("/test/mapping")
	public ResponseEntity<Test> mapping(@RequestBody Test test) {
		log.info("test = {}", test);
		return ResponseEntity.ok(test);
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Test {

		private String name;
		private int age;

		@Builder
		public Test(String name, int age) {
			this.name = name;
			this.age = age;
		}
	}
}
