package com.example.inflearnbatch.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepContributionBatch {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	Job stepContributionJob() {
		return jobBuilderFactory.get("stepContributionJob")
			.start(stepContributionStep())
			.build();
	}

	@Bean
	Step stepContributionStep() {
		return stepBuilderFactory.get("stepContributionStep")
			.tasklet((contribution, chunkContext) -> {
				log.info("======================================");
				log.info("Hello stepContributionStep!");
				log.info("======================================");

				return RepeatStatus.FINISHED;
			})
			.build();
	}
}
