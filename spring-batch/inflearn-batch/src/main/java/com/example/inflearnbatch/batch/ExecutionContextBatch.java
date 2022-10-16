package com.example.inflearnbatch.batch;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ExecutionContextBatch {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	Job executionContextJob() {
		return jobBuilderFactory.get("executionContextJob")
			.start(executionContextStep1())
			.next(executionContextStep2())
			.next(executionContextStep3())
			.next(executionContextStep4())
			.build();
	}

	@Bean
	Step executionContextStep1() {
		return stepBuilderFactory.get("executionContextStep1")
			.tasklet((contribution, chunkContext) -> {
				ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
				ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();
				Map<String, Object> stepExecutionContextMap = chunkContext.getStepContext().getStepExecutionContext();

				log.info("job-name : {}", jobExecutionContext.get("jobName"));
				log.info("step-name-1 : {}", stepExecutionContext.get("stepName"));
				log.info("step-name-2 : {}", stepExecutionContextMap.get("stepName"));

				if (jobExecutionContext.get("jobName") == null)
					jobExecutionContext.put("jobName", "executionContextJob");

				if (stepExecutionContext.get("stepName") == null)
					stepExecutionContext.put("stepName", "executionContextStep1");

				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	Step executionContextStep2() {
		return stepBuilderFactory.get("executionContextStep2")
			.tasklet((contribution, chunkContext) -> {

				/*
				Collections.unmodifiableMap(result) read-only map 이므로 put 불가
				Map<String, Object> stepExecutionContextMap = chunkContext.getStepContext().getStepExecutionContext();
				stepExecutionContextMap.putIfAbsent("stepName", "executionContextStep2");
				*/

				ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
				log.info("step-name : {}", executionContext.get("stepName"));
				if (executionContext.get("stepName") == null) {
					executionContext.put("stepName", "executionContextStep2");
				}

				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	Step executionContextStep3() {
		return stepBuilderFactory.get("executionContextStep3")
			.tasklet((contribution, chunkContext) -> {
				// throw new RuntimeException("Eat this");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	Step executionContextStep4() {
		return stepBuilderFactory.get("executionContextStep4")
			.tasklet((contribution, chunkContext) -> {
				// 재시도할 때, 이전에 저장해둔 ExecutionContext를 가져와서 사용할 수 있다 ?!
				log.info("job-name : {}", chunkContext.getStepContext().getJobExecutionContext().get("jobName"));
				log.info("step-name : {}", chunkContext.getStepContext().getStepExecutionContext().get("stepName"));

				return RepeatStatus.FINISHED;
			})
			.build();
	}
}

/*
JobExecutionContext는 Step 간 공유가 되니 출력이 가능하고
StepExecutionContext는 Step 간 공유 되지 않는다
 */