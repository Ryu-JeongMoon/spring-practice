package hello.proxy.pure.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DecoratorPatternClient {

  private final Component component;

  public void execute() {
    String result = component.operate();
    log.info("result = {}", result);
  }

}
