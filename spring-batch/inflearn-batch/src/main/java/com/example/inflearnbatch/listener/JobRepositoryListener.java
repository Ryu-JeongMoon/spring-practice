package com.example.inflearnbatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobRepositoryListener implements JobExecutionListener {

	private final JobRepository jobRepository;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info("[JobRepositoryListener] beforeJob");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		String jobName = jobExecution.getJobInstance().getJobName();
		JobParameters jobParameters = jobExecution.getJobParameters();

		JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);
		assert lastJobExecution != null;

		ExecutionContext executionContext = lastJobExecution.getExecutionContext();
		log.info("[JobRepositoryListener] afterJob: {}", executionContext);
	}
}
