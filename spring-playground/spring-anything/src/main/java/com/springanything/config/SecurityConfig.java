package com.springanything.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable) // REST-API의 경우 csrf 토큰을 사용하지 않음
			// .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer
			// 	.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

			.authorizeRequests()
			.anyRequest().permitAll()
			.and()

			.headers()
			.xssProtection()
			.and()
			.contentSecurityPolicy("script-src 'self");
	}
}

/*
.csrf(AbstractHttpConfigurer::disable)
Spring-Security 설정을 한 후, POST 요청에서만 403 에러가 발생하는 경우
html에서 아래와 같이 type="hidden"으로 csrf 토큰을 넣어주면 해결됨
<input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>
 */