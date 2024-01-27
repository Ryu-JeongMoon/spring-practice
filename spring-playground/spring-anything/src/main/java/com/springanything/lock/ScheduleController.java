package com.springanything.lock;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@ConditionalOnBean(Schedule.class)
public class ScheduleController {

  private final Schedule schedule;
  private final StringRedisTemplate stringRedisTemplate;

  /* Lock과 상관 없이 응답은 바로 됨 */
  @GetMapping("/test-schedule")
  public String schedule() {
    schedule.run2();
    return "ok";
  }

  @GetMapping("/test-unlock/{method}")
  public String unlock(@PathVariable String method) {
    String lockPrefix = "job-lock:default:";
    stringRedisTemplate.delete(lockPrefix + method);
    return "ok";
  }
}
