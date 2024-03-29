package com.example.inflearnbatch.theory.config;

import javax.sql.DataSource;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;

// @Configuration
public class CustomBatchConfigurer extends BasicBatchConfigurer {

	private final DataSource dataSource;

	/**
	 * Create a new {@link BasicBatchConfigurer} instance.
	 *
	 * @param properties                    the batch properties
	 * @param dataSource                    the underlying data source
	 * @param transactionManagerCustomizers transaction manager customizers (or
	 *                                      {@code null})
	 */
	protected CustomBatchConfigurer(
		BatchProperties properties,
		DataSource dataSource,
		TransactionManagerCustomizers transactionManagerCustomizers
	) {
		super(properties, dataSource, transactionManagerCustomizers);
		this.dataSource = dataSource;
	}

	// JobRepository Custom Point
	@Override
	protected JobRepository createJobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(dataSource);
		factory.setTransactionManager(getTransactionManager());
		factory.setTablePrefix("SYSTEM_");
		factory.setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ");
		return factory.getObject();
	}
}