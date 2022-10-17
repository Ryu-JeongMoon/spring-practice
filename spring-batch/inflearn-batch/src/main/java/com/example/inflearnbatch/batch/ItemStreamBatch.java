package com.example.inflearnbatch.batch;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.inflearnbatch.CustomItemStreamReader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ItemStreamBatch {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	Job itemStreamJob() {
		return jobBuilderFactory.get("itemStreamJob")
			.start(itemStreamStep())
			.build();
	}

	@Bean
	Step itemStreamStep() {
		return stepBuilderFactory.get("itemStreamStep")
			.<Integer, String>chunk(2)
			.reader(new CustomItemStreamReader<>(List.of(1, 3, 5, 7, 9, 11, 13, 15, 17)))
			.processor((ItemProcessor<Integer, String>)item -> item + ", Spring Batch")
			.writer(items -> log.info("items : {}", items))
			.build();
	}
}
