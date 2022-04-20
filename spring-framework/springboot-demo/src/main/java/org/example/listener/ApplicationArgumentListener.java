package org.example.listener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class ApplicationArgumentListener {

  public ApplicationArgumentListener(ApplicationArguments arguments) {
    System.out.println("arguments.containsOption('foo') ? => " + arguments.containsOption("foo"));
    System.out.println("arguments.containsOption('bar') ? => " + arguments.containsOption("bar"));
  }
}

/*
JVM option 은 ApplicationArguments 에 속하지 않는다
CLI arguments 만 여기서 뽑아와 사용할 수 있는데 써야할 필요가 있는고?
 */