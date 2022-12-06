package com.springanything.mvc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(excludeFilters = @ComponentScan.Filter(Configuration.class))
@Import(DelegateConfig.class)
public class MainConfig {

}
