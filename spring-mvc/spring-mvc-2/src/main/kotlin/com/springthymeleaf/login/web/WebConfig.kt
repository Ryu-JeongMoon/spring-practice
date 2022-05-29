package com.springthymeleaf.login.web

import com.springthymeleaf.login.web.annotation.LoginMemberArgumentResolver
import com.springthymeleaf.login.web.filter.LogFilter
import com.springthymeleaf.login.web.filter.LoginCheckFilter
import com.springthymeleaf.login.web.filter.WHITE_LIST
import com.springthymeleaf.login.web.interceptor.LogInterceptor
import com.springthymeleaf.login.web.interceptor.LoginCheckInterceptor
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.Filter


@Configuration
class WebConfig : WebMvcConfigurer {

  override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
    resolvers.add(LoginMemberArgumentResolver())
  }

  override fun addInterceptors(registry: InterceptorRegistry) {
    registry.addInterceptor(LogInterceptor())
      .order(1)
      .addPathPatterns("/**")
      .excludePathPatterns(*WHITE_LIST)

    registry.addInterceptor(LoginCheckInterceptor())
      .order(2)
      .addPathPatterns("/**")
      .excludePathPatterns(*WHITE_LIST)
  }

  //  @Bean
  fun filterRegistrationBean(): FilterRegistrationBean<Filter> {
    val filterRegistrationBean = FilterRegistrationBean<Filter>()
    filterRegistrationBean.filter = LogFilter()
    filterRegistrationBean.order = 1
    filterRegistrationBean.urlPatterns = listOf("/*")
    return filterRegistrationBean
  }

  //  @Bean
  fun loginCheckFilter(): FilterRegistrationBean<Filter>? {
    val filterRegistrationBean = FilterRegistrationBean<Filter>()
    filterRegistrationBean.filter = LoginCheckFilter()
    filterRegistrationBean.order = 2
    filterRegistrationBean.urlPatterns = listOf("/*")
    return filterRegistrationBean
  }
}