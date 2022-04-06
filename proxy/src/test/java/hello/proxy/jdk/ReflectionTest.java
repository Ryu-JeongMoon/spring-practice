package hello.proxy.jdk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class ReflectionTest {

  @Test
  @DisplayName("")
  void withoutReflection() {
    Hello target = new Hello();

    // 공통로직 1
    log.info("start");
    String resultA = target.methodA();
    log.info("result = {}", resultA);

    // 공통로직 2
    log.info("start");
    String resultB = target.methodB();
    log.info("result = {}", resultB);
  }

  @Test
  @DisplayName("")
  void withReflection() throws Exception {
    Class<?> helloClass = Class.forName("hello.proxy.jdk.ReflectionTest$Hello");

    Hello target = new Hello();
    Method methodA = helloClass.getMethod("methodA");
    Method methodB = helloClass.getMethod("methodB");

    Object resultA = methodA.invoke(target);
    log.info("result = {}", resultA);

    Object resultB = methodB.invoke(target);
    log.info("result = {}", resultB);
  }

  @Test
  @DisplayName("")
  void withAdvancedReflection() throws Exception {
    Class<?> helloClass = Class.forName("hello.proxy.jdk.ReflectionTest$Hello");
    Method methodA = helloClass.getMethod("methodA");
    Method methodB = helloClass.getMethod("methodB");
    Hello target = new Hello();

    dynamicCall(methodA, target);
    dynamicCall(methodB, target);
  }

  private void dynamicCall(Method method, Object target)
    throws InvocationTargetException, IllegalAccessException {
    log.info("start");
    Object result = method.invoke(target);
    log.info("result = {}", result);
  }


  static class Hello {

    public String methodA() {
      log.info("methodA called");
      return "A";
    }

    public String methodB() {
      log.info("methodB called");
      return "B";
    }
  }
}

/*
Reflection 런타임에 유연하게 갈아낄 수 있지만 오타에 취약해 런타임에 터지게 한다
 */