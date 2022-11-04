package com.example.inflearnbatch.core.p6spy;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import com.p6spy.engine.spy.P6SpyOptions;

@Configuration
public class P6spyConfig {

	@PostConstruct
	public void setLogMessageFormat() {
		P6SpyOptions
			.getActiveInstance()
			.setLogMessageFormat(P6spyPrettySqlFormatter.class.getName());
	}
}
