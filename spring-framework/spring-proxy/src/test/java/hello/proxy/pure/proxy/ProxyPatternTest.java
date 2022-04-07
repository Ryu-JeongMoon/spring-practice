package hello.proxy.pure.proxy;

import hello.proxy.pure.proxy.code.ProxyCacheSubject;
import hello.proxy.pure.proxy.code.ProxyPatternClient;
import hello.proxy.pure.proxy.code.RealSubject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

  @Test
  @DisplayName("RealSubject 3번 호출")
  void noProxy() {
    RealSubject realSubject = new RealSubject();
    ProxyPatternClient client = new ProxyPatternClient(realSubject);

    client.execute();
    client.execute();
    client.execute();
  }

  @Test
  @DisplayName("RealSubject 1번 호출")
  void proxy() {
    RealSubject realSubject = new RealSubject();
    ProxyCacheSubject proxyCacheSubject = new ProxyCacheSubject(realSubject);
    ProxyPatternClient client = new ProxyPatternClient(proxyCacheSubject);

    client.execute();
    client.execute();
    client.execute();
  }
}
