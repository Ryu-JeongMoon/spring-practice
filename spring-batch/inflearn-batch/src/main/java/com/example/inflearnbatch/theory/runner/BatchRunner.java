package com.example.inflearnbatch.theory.runner;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BatchRunner implements ApplicationRunner {

	protected final Job job;
	protected final JobLauncher jobLauncher;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
			// .addDate("version", new Date())
			// .addString("version", "1")
			.addString("requestDate", "20210102")
			.toJobParameters();

		jobLauncher.run(job, jobParameters);
	}
}
