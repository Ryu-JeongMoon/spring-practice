package com.springanything.config;

import static com.springanything.config.DataSourceConfig.PACKAGE_TO_SCAN;

import javax.sql.DataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.querydsl.jpa.impl.JPAProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EntityScan(basePackages = PACKAGE_TO_SCAN)
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = PACKAGE_TO_SCAN)
@ConditionalOnProperty(prefix = "conditional.enabled", value = "jta", havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
public class DataSourceConfig {

  public static final String PACKAGE_TO_SCAN = "com.springanything";

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.hikari")
  public DataSource jpaDataSource() {
    return DataSourceBuilder.create()
      .type(HikariDataSource.class)
      .build();
  }

  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }

  @Bean
  public JPAQueryFactory queryFactory(EntityManager entityManager) {
    return new JPAQueryFactory(JPAProvider.getTemplates(entityManager), entityManager);
  }
}
