package hello.proxy.concrete;

import hello.proxy.concrete.code.ConcreteClient;
import hello.proxy.concrete.code.ConcreteLogic;
import hello.proxy.concrete.code.ConcreteLogicProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

  @Test
  @DisplayName("")
  void noProxy() {
    ConcreteLogic concreteLogic = new ConcreteLogic();
    ConcreteClient client = new ConcreteClient(concreteLogic);
    client.execute();
  }

  @Test
  @DisplayName("")
  void withProxy() {
    ConcreteLogicProxy concreteLogicProxy = new ConcreteLogicProxy();
    ConcreteClient client = new ConcreteClient(concreteLogicProxy);
    client.execute();
  }
}
