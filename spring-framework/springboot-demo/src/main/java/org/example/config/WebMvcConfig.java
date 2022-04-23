package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

}

/*
@EnableWebMvc 붙이는 순간 AutoConfigure 되는 속성들 사라지고 여기서 전부 설정해야 함
ContentsNegotiation 굉장히 귀찮기 땜시 기본 설정 사용하도록 하고
위와 같이 @Configuration + WebMvcConfigurer 구현해서 커스터마이징해서 사용할 것
 */