package com.example.ioc.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class ResourceRunner implements ApplicationRunner {

  private final ResourceLoader resourceLoader;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("=================================================================");
    System.out.println("ResourceRunner.run");

    Resource resource = resourceLoader.getResource("classpath:application.txt");
    System.out.println("filename = " + resource.getFilename());
    System.out.println("description = " + resource.getDescription());
    System.out.println("uri = " + resource.getURI());
    System.out.println("url = " + resource.getURL());
    System.out.println("Files.readString(Path.of(uri)) = " + Files.readString(Path.of(resource.getURI())));

    System.out.println("=================================================================\n");
  }
}

/*
상위에 여러 인터페이스가 있다면 그 중 가장 구체적인 인터페이스를 사용하는 것이 낫다
무작정 최상위를 쓰는 것보다 직관적인 형태로 사용하는 것이 좋기 때문이다
더 특화된 메서드가 있을 수도 있고 타인이 봤을 때 어떤 기능을 사용하는 지가 명확하게 보인다

여기서는 ApplicationContext 사용할 수 있으나 더 구체적인 ResourceLoader 사용했다
 */