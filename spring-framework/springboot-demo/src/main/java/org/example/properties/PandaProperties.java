package org.example.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties("panda")
public class PandaProperties {

  private final String name;
  private final int age;
  private final int weight;
}

/*
final 설정 안 해두면 <Spring no setter found for property> 터짐
 */