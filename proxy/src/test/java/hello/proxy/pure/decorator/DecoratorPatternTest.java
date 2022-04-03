package hello.proxy.pure.decorator;

import hello.proxy.pure.decorator.code.DecoratorPatternClient;
import hello.proxy.pure.decorator.code.ExecutionTimeLoggingComponent;
import hello.proxy.pure.decorator.code.MessageDecoratorComponent;
import hello.proxy.pure.decorator.code.RealComponent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

  @Test
  @DisplayName("적용 전")
  void noDecorator() {
    RealComponent realComponent = new RealComponent();
    DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
    client.execute();
  }

  @Test
  @DisplayName("적용 후")
  void decorator() {
    RealComponent realComponent = new RealComponent();
    MessageDecoratorComponent messageDecoratorComponent = new MessageDecoratorComponent(realComponent);
    DecoratorPatternClient client = new DecoratorPatternClient(messageDecoratorComponent);

    client.execute();
  }

  @Test
  @DisplayName("Decorator Chaining")
  void decoratorChain() {
    RealComponent realComponent = new RealComponent();
    MessageDecoratorComponent messageDecorator = new MessageDecoratorComponent(realComponent);
    ExecutionTimeLoggingComponent timeLogger = new ExecutionTimeLoggingComponent(messageDecorator);
    DecoratorPatternClient client = new DecoratorPatternClient(timeLogger);
    client.execute();
  }
}
