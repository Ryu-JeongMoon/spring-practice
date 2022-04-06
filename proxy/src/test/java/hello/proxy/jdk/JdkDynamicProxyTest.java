package hello.proxy.jdk;

import hello.proxy.jdk.code.AImpl;
import hello.proxy.jdk.code.AInterface;
import hello.proxy.jdk.code.TimeInvocationHandler;
import java.lang.reflect.Proxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class JdkDynamicProxyTest {

  @Test
  @DisplayName("")
  void dynamic() {
    AInterface target = new AImpl();

    TimeInvocationHandler invocationHandler = new TimeInvocationHandler(target);
    AInterface proxy = (AInterface) Proxy.newProxyInstance(
      AInterface.class.getClassLoader(),
      new Class[]{ AInterface.class }, invocationHandler
    );

    proxy.call();
    log.info("targetClass = {}", target.getClass());
    log.info("proxyClass = {}", proxy.getClass());
  }
}
