package com.example.inflearnbatch.batch;

import static com.example.inflearnbatch.domain.QPanda.panda;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;

import com.example.inflearnbatch.domain.Panda;
import com.example.inflearnbatch.domain.PandaBackup;
import com.example.inflearnbatch.reader.QuerydslPagingItemReader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PandaBackupBatch {

  private final EntityManagerFactory emf;
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job pandaBackupJob() {
    return jobBuilderFactory.get("pandaBackupJob")
      .start(pandaBackupStep())
      .next(pandaBackupNextStep())
      .build();
  }

  @Bean
  public Step pandaBackupStep() {
    return stepBuilderFactory.get("pandaBackupStep")
      .<Panda, PandaBackup>chunk(50)
      .reader(reader())
      .processor(processor())
      .writer(writer())
      .build();
  }

  @Bean
  public QuerydslPagingItemReader<Panda> reader() {
    return new QuerydslPagingItemReader<>(10, emf, queryFactory -> queryFactory
      .selectFrom(panda)
      .where(panda.name.like("a%")));
  }

  @Bean
  public Step pandaBackupNextStep() {
    return stepBuilderFactory.get("pandaBackupNextStep")
      .<Panda, PandaBackup>chunk(10)
      .reader(nextReader())
      .processor(processor())
      .writer(writer())
      .faultTolerant()
      .backOffPolicy(new FixedBackOffPolicy())
      .retryLimit(2)
      .retry(Exception.class)
      .build();
  }

  // @Bean
  public QuerydslPagingItemReader<Panda> nextReader() {
    return new QuerydslPagingItemReader<>(10, emf, queryFactory -> queryFactory
      .selectFrom(panda)
      .where(panda.name.like("b%")));
  }

  private ItemProcessor<Panda, PandaBackup> processor() {
    return PandaBackup::new;
  }

  @Bean
  public JpaItemWriter<PandaBackup> writer() {
    return new JpaItemWriterBuilder<PandaBackup>()
      .entityManagerFactory(emf)
      .build();
  }
}
