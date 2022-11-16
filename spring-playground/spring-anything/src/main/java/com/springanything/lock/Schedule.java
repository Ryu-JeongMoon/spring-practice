package com.springanything.lock;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Schedule {

	@Scheduled(cron = "0/3 * * * * *", zone = "Asia/Seoul")
	@SchedulerLock(name = "run1", lockAtLeastFor = "1s", lockAtMostFor = "2s")
	void run1() throws InterruptedException {
		Thread.sleep(3000);
		log.info("Schedule.run1");
	}

	@Scheduled(cron = "0/3 * * * * *", zone = "Asia/Seoul")
	@SchedulerLock(name = "run1", lockAtLeastFor = "1s", lockAtMostFor = "2s")
	void run2() throws InterruptedException {
		Thread.sleep(3000);
		log.info("Schedule.run2");
	}

	@Scheduled(cron = "0/3 * * * * *", zone = "Asia/Seoul")
	@SchedulerLock(name = "run1", lockAtLeastFor = "1s", lockAtMostFor = "2s")
	void run3() throws InterruptedException {
		Thread.sleep(3000);
		log.info("Schedule.run3");
	}

	@Scheduled(cron = "0/3 * * * * *", zone = "Asia/Seoul")
	@SchedulerLock(name = "run1", lockAtLeastFor = "1s", lockAtMostFor = "2s")
	void run4() throws InterruptedException {
		Thread.sleep(3000);
		log.info("Schedule.run4");
	}
}

/*
지정한 시간에 하나의 인스턴스에서만 실행되도록
같은 이름의 lock 사용해 SchedulerLock 걸어뿌림
 */