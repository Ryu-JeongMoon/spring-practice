 package com.example.inflearnbatch.practice.batch.job.file;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.inflearnbatch.practice.batch.chunk.processor.FileItemProcessor;
import com.example.inflearnbatch.practice.batch.domain.Product;
import com.example.inflearnbatch.practice.batch.domain.model.ProductDto;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FileJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;
	private final FileItemProcessor fileItemProcessor;

	@Bean
	public Job fileJob() {
		return jobBuilderFactory.get("fileJob")
			.start(fileStep())
			.build();
	}

	@Bean
	public Step fileStep() {
		return stepBuilderFactory.get("fileStep")
			.<ProductDto.Request, Product>chunk(10)
			.reader(fileItemReader(null))
			.processor(fileItemProcessor)
			.writer(fileItemWriter())
			.build();
	}

	@Bean
	@StepScope
	public FlatFileItemReader<ProductDto.Request> fileItemReader(
		@Value("#{jobParameters[requestDate]}") String requestDate
	) {
		return new FlatFileItemReaderBuilder<ProductDto.Request>()
			.name("flatFile")
			.resource(new ClassPathResource("product_%s.csv".formatted(requestDate)))
			.fieldSetMapper(new BeanWrapperFieldSetMapper<>())
			.targetType(ProductDto.Request.class)
			.linesToSkip(1)
			.delimited().delimiter(",")
			.names("id", "name", "price", "type")
			.build();
	}

	@Bean
	public ItemWriter<Product> fileItemWriter() {
		return new JpaItemWriterBuilder<Product>()
			.entityManagerFactory(entityManagerFactory)
			.usePersist(true)
			.build();
	}
}
