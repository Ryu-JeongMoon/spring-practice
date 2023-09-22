package com.springanything.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ConfigBean.class)
public class DelegateConfig {

}
