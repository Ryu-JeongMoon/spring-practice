package org.example.runner;

import lombok.RequiredArgsConstructor;
import org.example.properties.PandaProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(PandaProperties.class)
public class PropertyRunner implements ApplicationRunner {

  @Value("${name}")
  private String name;

  private final PandaProperties pandaProperties;

  @Override
  public void run(ApplicationArguments args) {
    System.out.println("name = " + name);

    System.out.println("pandaProperties.getAge() = " + pandaProperties.getAge());
    System.out.println("pandaProperties.getName() = " + pandaProperties.getName());
    System.out.println("pandaProperties.getWeight() = " + pandaProperties.getWeight());
  }
}

/*
프로퍼티 파일을 읽어오는데엔 우선 순위가 있다~~ 이 말이다
1. file:./config/
2. file:./
3. classpath:/config/
4. classpath:/

프로젝트 루트에서 시작
config 폴더를 제일 먼저 뒤지고
없으면 루트
또 없으면 resources/config 뒤지고
또또 없으면 resources/ 뒤진다
 */