package com.springanything.lock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

	// private final Schedule schedule;

	/* Lock과 상관 없이 응답은 바로 됨 */
	@GetMapping("/test-schedule")
	public String schedule() {
		// schedule.run2();
		return "ok";
	}
}
