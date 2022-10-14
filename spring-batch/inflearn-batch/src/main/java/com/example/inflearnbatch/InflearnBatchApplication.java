package com.example.inflearnbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class InflearnBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(InflearnBatchApplication.class, args);
	}

}

/*
1. SimpleBatchConfiguration
PlatformTransactionManager, JobRepository, JobLauncher, JobRegistry, JobExplorer 등
기본 배치 설정과 관련한 작업 처리

2. BatchConfigurerConfiguration
EntityManagerFactory Bean이 존재하는지 아닌지에 따라 (spring-data-jpa 의존성이 있느냐)
- JdbcBatchConfiguration
- JpaBatchConfiguration (JpaBatchConfigurer extends BasicBatchConfigurer)

BatchConfigurer란?
- Batch System이 필요한 컴포넌트를 제공하는 Factory의 Interface

3. BatchAutoConfiguration
JobLauncherApplicationRunner Bean 생성
spring.batch.job 속성으로 Job 실행 조절
 */