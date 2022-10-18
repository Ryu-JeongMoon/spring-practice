package com.example.inflearnbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.inflearnbatch.config.QuerydslConfig;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
@EntityScan("com.example.inflearnbatch.domain")
@EnableJpaRepositories("com.example.inflearnbatch.domain.repo")
@Import(QuerydslConfig.class)
public class TestBatchConfig {

}
