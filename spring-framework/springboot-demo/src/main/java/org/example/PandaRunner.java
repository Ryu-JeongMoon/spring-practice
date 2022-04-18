package org.example;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PandaRunner implements ApplicationRunner {

  private final Panda panda;

  public PandaRunner(Panda panda) {
    this.panda = panda;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println(panda);
  }
}
