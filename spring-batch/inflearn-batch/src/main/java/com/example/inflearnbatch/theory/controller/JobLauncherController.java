package com.example.inflearnbatch.theory.controller;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JobLauncherController {

	private final Job executionContextJob;
	private final JobLauncher jobLauncher;
	private final BasicBatchConfigurer basicBatchConfigurer;

	@GetMapping("/job")
	public String launch(@RequestParam String version)
		throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
		JobParameters jobParameters = new JobParametersBuilder()
			.addString("version", version)
			.addDate("date", new Date())
			.toJobParameters();

		SimpleJobLauncher launcher = (SimpleJobLauncher)basicBatchConfigurer.getJobLauncher();
		// SimpleJobLauncher launcher = (SimpleJobLauncher)jobLauncher;
		launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());

		jobLauncher.run(executionContextJob, jobParameters);
		return executionContextJob.getName();
	}
}

/*
배치 비동기 처리

1. SimpleJobLauncher launcher = (SimpleJobLauncher)basicBatchConfigurer.getJobLauncher();
비동기 처리를 위한 수동 jobLauncher DI를 하기로 했다면
basicBatchConfigurer까지 받아와 SimpleJobLauncher로 캐스팅 후 setTaskExecutor를 호출해야 한다

2. private final JobLauncher jobLauncher;
JobLauncher 인터페이스에는 존재하지 않는 API 라서 SimpleJobLauncher 구현체로 캐스팅을 해줘야 한다
JobLauncher는 Job을 실행하는 인터페이스인데 기본 구현체로는 SimpleJobLauncher가 있다
SimpleJobLauncher는 JDK Dynamic Proxy에 의해 생성되어 jdk.proxy2.$Proxy81 형태로 만들어진다
Proxy에 setTaskExecutor를 호출하려 SimpleJobLauncher로 캐스팅하면 ClassCastException이 발생한다

3. private final SimpleJobLauncher jobLauncher;
위 형태로 직접 구현체 주입을 받아보려 했으나 Proxy로 만들어 껴주는 것이기 때문에 생성 시점에 주입이 되지 않는다
 */