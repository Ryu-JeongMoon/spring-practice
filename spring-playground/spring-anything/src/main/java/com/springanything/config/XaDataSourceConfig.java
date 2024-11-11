package com.springanything.config;

import javax.sql.DataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.spring.AtomikosDataSourceBean;
import com.querydsl.jpa.impl.JPAProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Configuration
@EnableTransactionManagement
@ConditionalOnMissingBean(DataSourceConfig.class)
public class XaDataSourceConfig {

  public static final String MYSQL_XA_DATASOURCE = "com.mysql.cj.jdbc.MysqlXADataSource";

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.jpa.xa")
  public DataSource jpaDataSource() {
    AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
    atomikosDataSourceBean.setBeanName("jpaXaDataSource");
    atomikosDataSourceBean.setXaDataSourceClassName(MYSQL_XA_DATASOURCE);
    return atomikosDataSourceBean;
  }

  @Bean
  @Primary
  public EntityManagerFactory entityManagerFactory(DataSource jpaXaDataSource, JpaProperties jpaProperties) {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setPersistenceUnitName("xa-default");
    factoryBean.setDataSource(jpaXaDataSource);
    factoryBean.setPackagesToScan("com.springanything");
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
  @ConfigurationProperties(prefix = "spring.datasource.my-batis.xa")
  public DataSource myBatisXaDataSource() {
    AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
    atomikosDataSourceBean.setBeanName("myBatisXaDataSource");
    atomikosDataSourceBean.setXaDataSourceClassName(MYSQL_XA_DATASOURCE);
    return atomikosDataSourceBean;
  }

  @Bean
  @Primary
  public SqlSessionFactory sqlSessionFactory(ResourceLoader resourceLoader) throws Exception {
    ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);

    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(myBatisXaDataSource());
    factoryBean.setMapperLocations(patternResolver.getResources("classpath:/mapper/**/*.xml"));
    factoryBean.setTypeAliasesPackage("com.example.mybatis");
    factoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
    factoryBean.afterPropertiesSet();
    return factoryBean.getObject();
  }

  @Bean
  public UserTransactionManager userTransactionManager() throws SystemException {
    UserTransactionManager userTransactionManager = new UserTransactionManager();
    userTransactionManager.setTransactionTimeout(1000);
    userTransactionManager.setForceShutdown(true);
    return userTransactionManager;
  }

  @Bean
  public UserTransaction userTransaction() throws SystemException {
    UserTransactionImp userTransaction = new UserTransactionImp();
    userTransaction.setTransactionTimeout(60000);
    return userTransaction;
  }

  @Bean
  @Primary
  public JtaTransactionManager transactionManager(UserTransactionManager userTransactionManager, UserTransaction userTransaction) {
    JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
    jtaTransactionManager.setTransactionManager(userTransactionManager);
    jtaTransactionManager.setUserTransaction(userTransaction);
    jtaTransactionManager.afterPropertiesSet();
    return jtaTransactionManager;
  }
}
