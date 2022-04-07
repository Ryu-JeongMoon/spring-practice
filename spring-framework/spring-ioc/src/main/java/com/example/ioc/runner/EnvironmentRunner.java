package com.example.ioc.runner;

import com.example.ioc.TestBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvironmentRunner implements ApplicationRunner {

  private final ApplicationContext context;
  private final TestBookRepository testBookRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("=================================================================");
    System.out.println("EnvironmentRunner.run");

    System.out.println(context.getEnvironment().getProperty("app.name"));
    System.out.println(context.getEnvironment().getProperty("yaho"));

    System.out.println("=================================================================\n");
  }
}

/*
ApplicationContext 는 EnvironmentCapable 를 상속 받는다
얘한테는 getEnvironment method 가 있고 이 메서드를 통해 현재의 실행 환경 정보를 가져올 수 있다
ActiveProfile, DefaultProfile 등 가져온다
현재 실행 중인 Profile 에 해당하는 빈들을 가져오므로 애플리케이션을 stage 별로 나눠 사용
주로 test / develop / release or qa / prod 정도로 나눠 운용

VM option 에 -D[key]=[value] 형태로 넣어주면 environment.getProperty(key) 로 value 값 가져다 쓸 수 있ㅇ므
여러개 넣을 시 각각 -D 붙여서 넣어줘야함
ex) -Dapp.name=spring5 -Dyaho=panda

VM option 에 넣어준 설정 값이 .properties, .yml 설정 파일보다 우선순위가 높다
 */
