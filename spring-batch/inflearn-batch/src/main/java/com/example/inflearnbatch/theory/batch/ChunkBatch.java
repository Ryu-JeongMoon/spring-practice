package com.example.inflearnbatch.theory.batch;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChunkBatch {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	Job chunkJob() {
		return jobBuilderFactory.get("chunkJob")
			.start(chunkStep())
			.build();
	}

	@Bean
	Step chunkStep() {
		return stepBuilderFactory.get("chunkStep")
			.<String, String>chunk(2)
			.reader(new ListItemReader<>(List.of("item1", "item2", "item3", "item4", "item5", "item6")))
			.processor((ItemProcessor<String, String>)item -> item + ", Spring Batch")
			.writer(items -> log.info("items : {}", items))
			.build();
	}
}
