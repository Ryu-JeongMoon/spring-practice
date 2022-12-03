package com.example.inflearnbatch.batch;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RepositoryBatch extends AbstractBatch {

	@Bean(name = "repositoryJob")
	@Override
	protected Job job() {
		return jobBuilderFactory.get("repositoryJob")
			.start(step())
			.build();
	}

	@Bean(name = "repositoryStep")
	@Override
	protected Step step() {
		return stepBuilderFactory.get("repositoryStep")
			.<Integer, Integer>chunk(5)
			.reader(new ListItemReader<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)))
			.writer(items -> items.forEach(item -> log.info("item: {}", item)))
			.build();
	}
}
