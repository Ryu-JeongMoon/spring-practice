package com.springanything.security.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
@ConditionalOnProperty(value = "condition.enabled.method-security", havingValue = "true")
public class MethodSecurityConfig {

  public static final String SELF_AUTHORIZE =
    "@roleChecker.isSelf(#" + CustomAnnotationParameterNameDiscoverer.USER_ID_OBJECT + ")";

  @Bean
  public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
    DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setParameterNameDiscoverer(new CustomAnnotationParameterNameDiscoverer());
    return expressionHandler;
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
  @PreAuthorize(SELF_AUTHORIZE)
  public @interface SelfAuthorize {

  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
  @PreAuthorize("hasAuthority(T(com.springanything.security.method.Role).USER) && " + SELF_AUTHORIZE)
  public @interface UserSelfAuthorize {

  }
}
