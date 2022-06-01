package com.springthymeleaf.login.web

import com.springthymeleaf.exception.resolver.CustomExceptionHandler
import com.springthymeleaf.login.web.annotation.LoginMemberArgumentResolver
import com.springthymeleaf.login.web.filter.LogFilter
import com.springthymeleaf.login.web.filter.LoginCheckFilter
import com.springthymeleaf.login.web.filter.WHITE_LIST
import com.springthymeleaf.login.web.interceptor.LogInterceptor
import com.springthymeleaf.login.web.interceptor.LoginCheckInterceptor
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.servlet.DispatcherType
import javax.servlet.Filter


@Configuration
class WebConfig : WebMvcConfigurer {

  override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
    super.addArgumentResolvers(resolvers)
    resolvers.add(LoginMemberArgumentResolver())
  }

//  override fun extendHandlerExceptionResolvers(resolvers: MutableList<HandlerExceptionResolver>) {
//    resolvers.add(CustomExceptionHandler())
//  }

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

  @Bean
  fun filterRegistrationBean(): FilterRegistrationBean<Filter> {
    val filterRegistrationBean = FilterRegistrationBean<Filter>()
    filterRegistrationBean.filter = LogFilter()
    filterRegistrationBean.order = 1
    filterRegistrationBean.urlPatterns = listOf("/*")
    filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR)
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

/*
사용자의 호출인지, WAS 내부 호출인지 구별하기 위해 DispatcherType을 이용할 수 있다
REQUEST, ERROR, ASYNC ... 등 다양한 옵션으로 커스터마이징할 수 있다
 */