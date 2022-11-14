package com.example.inflearnbatch.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public abstract class AbstractBatchController {

	protected final Logger log = LoggerFactory.getLogger(getClass().getName());

	@Autowired
	private ApplicationContext applicationContext;

	@GetMapping("/{batchName}")
	public String simpleNumberBatch(@PathVariable String batchName) {
		AbstractBatch batch = applicationContext.getBean(batchName, AbstractBatch.class);
		batch.executeByScheduling();
		return "ok";
	}
}
