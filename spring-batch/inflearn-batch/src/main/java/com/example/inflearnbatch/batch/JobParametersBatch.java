package com.example.inflearnbatch.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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
public class JobParametersBatch {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	Job parametersJob() {
		return jobBuilderFactory.get("parametersJob")
			.start(parametersStep())
			.build();
	}

	@Bean
	Step parametersStep() {
		return stepBuilderFactory.get("parametersStep")
			.tasklet((contribution, chunkContext) -> {
				// JobContribution에서 뽑으면 JobParameters 객체로 반환
				JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
				log.info("jobParameters: {}", jobParameters);

				// ChunkContext에서 뽑으면 Map<String, Object> 쌩으로 반환
				chunkContext.getStepContext().getJobParameters().forEach((key, value) -> log.info("key: {}, value: {}", key, value));

				return RepeatStatus.FINISHED;
			})
			.build();
	}
}