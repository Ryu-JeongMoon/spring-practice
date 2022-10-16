package com.example.inflearnbatch.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class HelloWorld {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job helloWorldJob() {
		return jobBuilderFactory.get("helloWorldJob")
			.start(helloWorldStep1())
			.next(helloWorldStep2())
			.build();
	}

	@Bean
	protected Step helloWorldStep1() {
		return stepBuilderFactory.get("helloWorldStep1")
			.tasklet((contribution, chunkContext) -> {
				System.out.println("======================================");
				System.out.println("Hello World!");
				System.out.println("======================================");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	Step helloWorldStep2() {
		return stepBuilderFactory.get("helloWorldStep2")
			.tasklet((contribution, chunkContext) -> {
				System.out.println("======================================");
				System.out.println("Hello World Again!");
				System.out.println("======================================");
				return RepeatStatus.FINISHED;
			})
			.build();
	}
}
