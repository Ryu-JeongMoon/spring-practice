package com.example.inflearnbatch.batch;

import java.util.Date;

import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBatch {

	protected final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	protected EntityManagerFactory emf;

	@Autowired
	protected JobLauncher jobLauncher;

	@Autowired
	protected JobBuilderFactory jobBuilderFactory;

	@Autowired
	protected StepBuilderFactory stepBuilderFactory;

	protected void executeByScheduling() {
		try {
			jobLauncher.run(job(), generateJobParameters());
		} catch (Exception e) {
			log.error("Error while executing job", e);
		}
	}

	protected abstract Job job();

	protected abstract Step step();

	private JobParameters generateJobParameters() {
		return new JobParametersBuilder()
			.addDate("date", new Date())
			.toJobParameters();
	}
}
