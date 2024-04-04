package com.springanything.config;

import static com.springanything.config.JpaConfig.PACKAGE_TO_SCAN;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import com.querydsl.jpa.impl.JPAProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EntityScan(basePackages = PACKAGE_TO_SCAN)
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = PACKAGE_TO_SCAN)
@ConditionalOnProperty(name = "spring.jta.enabled", havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
public class JpaConfig {

  public static final String PACKAGE_TO_SCAN = "com.springanything";

  private final JpaProperties jpaProperties;

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.jpa.hikari")
  public DataSource jpaDataSource() {
    return DataSourceBuilder.create()
      .type(HikariDataSource.class)
      .build();
  }

  @Bean
  @Primary
  public EntityManagerFactory entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setPersistenceUnitName("default");
    factoryBean.setDataSource(jpaDataSource());
    factoryBean.setPackagesToScan(PACKAGE_TO_SCAN);
    factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factoryBean.setJpaPropertyMap(jpaProperties.getProperties());
    factoryBean.afterPropertiesSet();
    return factoryBean.getObject();
  }

  @Bean
  public JPAQueryFactory queryFactory(EntityManager entityManager) {
    return new JPAQueryFactory(JPAProvider.getTemplates(entityManager), entityManager);
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory());
    return transactionManager;
  }

  @Bean
  @Primary
  @SuppressWarnings("deprecation")
  public TransactionManager chainedTransactionManager(
    @Qualifier("myBatisDataSource") DataSource myBatisDataSource
  ) {
    return new ChainedTransactionManager(transactionManager(), new DataSourceTransactionManager(myBatisDataSource));
  }
}
