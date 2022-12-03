package org.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("panda").password("{noop}1234").roles("USER").and()
			.withUser("bear").password("{noop}1234").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequests -> authorizeRequests
				.antMatchers("/", "/info", "/account/**", "/test/**").permitAll()
				.anyRequest().authenticated())
			.formLogin(formLogin ->
				formLogin.loginPage("/login")
					.permitAll())
			.rememberMe(Customizer.withDefaults());
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/test/**");
	}
}

/*
인메모리 설정 방법, 아마 쓸 일 없을 듯
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  auth.inMemoryAuthentication()
    .withUser("panda").password("{noop}1234").roles("USER").and()
    .withUser("bear").password("{noop}1234").roles("ADMIN");
}

** Spring Security without the WebSecurityConfigurerAdapter **
https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
왜 없어졌는가?, component-based security configuration 으로 이동시키기 위함
 */
