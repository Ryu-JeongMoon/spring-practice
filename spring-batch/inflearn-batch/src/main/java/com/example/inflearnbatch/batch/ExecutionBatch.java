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
public class ExecutionBatch {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	Job executionJob() {
		return jobBuilderFactory.get("executionJob")
			.start(executionStep())
			.build();
	}

	@Bean
	Step executionStep() {
		return stepBuilderFactory.get("executionStep")
			.tasklet((contribution, chunkContext) -> {
				System.out.println("======================================");
				System.out.println("Hello World!");
				System.out.println("======================================");

				// throw new RuntimeException();
				return RepeatStatus.FINISHED;
			})
			.build();
	}
}

/*
JobExecution은 JobInstance를 실행한 결과를 담고 있다.
JobParameters가 같더라도 Job이 실행만 가능하다면 매번 새로 생성된다.
(이전 실행에서 문제가 발생한 경우에만 다시 실행이 가능하다)

Job -> step1, step2, step3 이 있다고 할 때
step1 성공, step2 실패라면 기본적으로 재시도에서 step1 은 스킵하고 2, 3을 진행한다

동일 JobParameters로 Job 실행하면 JobInstanceAlreadyCompleteException 발생
동일 JobParameters여도 Job의 BatchStatus가 FAILED 라면 재실행 가능
 */