package com.springanything.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable) // REST-API의 경우 csrf 토큰을 사용하지 않음
			// .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer
			// 	.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))

			.authorizeRequests(authorizeRequestConfigurer ->
				authorizeRequestConfigurer
					.anyRequest().permitAll())
			.headers(httpSecurityHeadersConfigurer ->
				httpSecurityHeadersConfigurer
					.xssProtection(xssCustomizer -> xssCustomizer.xssProtectionEnabled(true))
					.contentSecurityPolicy(customizer -> customizer.policyDirectives("script-src 'self")))
			.build();
	}
}

/*
.csrf(AbstractHttpConfigurer::disable)
Spring-Security 설정을 한 후, POST 요청에서만 403 에러가 발생하는 경우
html에서 아래와 같이 type="hidden"으로 csrf 토큰을 넣어주면 해결됨
<input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}"/>
 */