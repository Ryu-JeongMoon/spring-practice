package com.example.inflearnbatch.theory.runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

@Component
public class JobRunner extends BatchRunner {

	/**
	 * Job 이름에 실행하고 싶은 Job Bean 이름을 받는다
	 */
	public JobRunner(Job fileJob, JobLauncher jobLauncher) {
		super(fileJob, jobLauncher);
	}
}