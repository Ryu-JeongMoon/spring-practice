package hello.proxy.pure.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageDecoratorComponent implements Component {

  private final Component target;

  @Override
  public String operate() {
    log.info("MessageDecoratorComponent Called");
    return "*".repeat(5) + target.operate() + "*".repeat(5);
  }
}
