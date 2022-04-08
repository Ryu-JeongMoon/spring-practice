package org.securitydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .mvcMatchers("/", "/info", "/account/**").permitAll()
      .mvcMatchers("/admin").hasRole("ADMIN")
      .anyRequest().authenticated()

      .and()
      .formLogin()

      .and()
      .httpBasic();
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
 */
