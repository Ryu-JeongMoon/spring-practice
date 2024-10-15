package com.springanything.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

@Configuration
@ConditionalOnProperty(value = "condition.enabled.chained-transaction", havingValue = "true")
public class ChainedTransactionConfig {

  @Bean
  @Primary
  @SuppressWarnings("deprecation")
  public TransactionManager chainedTransactionManager(
    PlatformTransactionManager transactionManager,
    @Qualifier("myBatisDataSource") DataSource myBatisDataSource
  ) {
    return new ChainedTransactionManager(transactionManager, new DataSourceTransactionManager(myBatisDataSource));
  }
}
