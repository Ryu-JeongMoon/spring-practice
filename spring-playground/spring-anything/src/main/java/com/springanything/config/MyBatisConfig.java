package com.springanything.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;

@Configuration
@MapperScan(basePackages = "com.springanything.mybatis")
@ConditionalOnProperty(name = "spring.jta.enabled", havingValue = "false", matchIfMissing = true)
@RequiredArgsConstructor
public class MyBatisConfig {

  private final ResourceLoader resourceLoader;

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.my-batis.hikari")
  public DataSource myBatisDataSource() {
    return DataSourceBuilder.create()
      .type(HikariDataSource.class)
      .build();
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);

    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(myBatisDataSource());
    factoryBean.setMapperLocations(patternResolver.getResources("classpath:/mapper/**/*.xml"));
    factoryBean.setTypeAliasesPackage("com.example.mybatis");
    factoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
    factoryBean.afterPropertiesSet();
    return factoryBean.getObject();
  }

  @Bean
  @ConfigurationProperties(prefix = "mybatis.configuration")
  public SqlSessionTemplate sqlSession() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory());
  }

  @Bean
  org.apache.ibatis.session.Configuration mybatisConfiguration() {
    return new org.apache.ibatis.session.Configuration();
  }
}
