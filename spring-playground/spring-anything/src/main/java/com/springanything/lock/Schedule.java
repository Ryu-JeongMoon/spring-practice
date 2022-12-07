package com.springanything.lock;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Schedule {

	@Scheduled(cron = "0/3 * * * * *", zone = "Asia/Seoul")
	@SchedulerLock(name = "run1", lockAtLeastFor = "1s", lockAtMostFor = "3s")
	void run1() throws InterruptedException {
		Thread.sleep(2500);
		log.info("Schedule.run1 at {}", new Date());
	}

	@SchedulerLock(name = "run1", lockAtLeastFor = "30s", lockAtMostFor = "30s")
	void run2() {
		log.info("Schedule.run2 at {}", new Date());
	}

	@Scheduled(cron = "0/3 * * * * *", zone = "Asia/Seoul")
	@SchedulerLock(name = "run1", lockAtLeastFor = "1s", lockAtMostFor = "3s")
	void run3() throws InterruptedException {
		Thread.sleep(2500);
		log.info("Schedule.run3 at {}", new Date());
	}

	@Scheduled(cron = "0/3 * * * * *", zone = "Asia/Seoul")
	@SchedulerLock(name = "run1", lockAtLeastFor = "1s", lockAtMostFor = "3s")
	void run4() throws InterruptedException {
		Thread.sleep(2500);
		log.info("Schedule.run4 at {}", new Date());
	}
}

/*
지정한 시간에 하나의 인스턴스에서만 실행되도록
같은 이름의 lock 사용해 SchedulerLock 걸어뿌림
 */