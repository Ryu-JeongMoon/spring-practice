package com.example.ioc.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceAbstractionRunner implements ApplicationRunner {

  private final ResourceLoader resourceLoader;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("=================================================================");
    System.out.println("ResourceAbstractionRunner.run");

    Resource resource = resourceLoader.getResource("classpath:application.txt");

    System.out.println("resourceLoader.getClass() = " + resourceLoader.getClass());
    System.out.println("resource.exists() = " + resource.exists());
    System.out.println("resource.getURI() = " + resource.getURI());
    System.out.println("resource.getDescription() = " + resource.getDescription());

    System.out.println("=================================================================\n");
  }
}

/*
resource 가져올 때 가져오려는 리소스의 경로를 주게 되는데
어디서부터 찾을지는 내가 사용하는 ApplicationContext 의 타입에 따라 달라진다
XML, FileSystem, WebServlet 등등

대부분이 WebServlet 을 사용해 프로젝트 루트부터 상대 경로로 찾아오는데
혼란을 방지하고자 classpath: 접두어를 붙여 사용하는 것이 명시적이어서 좋다
 */