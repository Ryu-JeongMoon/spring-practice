package com.example.inflearnbatch.theory.batch;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractBatch {

	protected final JobBuilderFactory jobBuilderFactory;
	protected final StepBuilderFactory stepBuilderFactory;

}
