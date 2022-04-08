package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

@Slf4j
public class AdvisorTest {

  @Test
  @DisplayName("DefaultPointcutAdvisor 사용")
  void advisorTest1() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
    proxyFactory.addAdvisor(advisor);

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
  }

  @Test
  @DisplayName("Pointcut 직접 만들어 사용")
  void advisorTest2() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
    proxyFactory.addAdvisor(advisor);

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
  }

  @Test
  @DisplayName("CustomPointcut 사용")
  void advisorTest3() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new CustomPointcut(), new TimeAdvice());
    proxyFactory.addAdvisor(advisor);

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
  }

  @Test
  @DisplayName("스프링이 제공하는 NameMatchMethodPointcut 사용")
  void advisorTest4() {
    ServiceInterface target = new ServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);

    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedNames("save");

    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
    proxyFactory.addAdvisor(advisor);

    ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

    proxy.save();
    proxy.find();
  }

  static class CustomPointcut implements Pointcut {

    private static class MethodMatcherHolder {

      private static final CustomMethodMatcher customMethodMatcher = new CustomMethodMatcher();
    }

    private static MethodMatcher getInstance() {
      return MethodMatcherHolder.customMethodMatcher;
    }

    @Override
    public ClassFilter getClassFilter() {
      return ClassFilter.TRUE;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
      return getInstance();
    }
  }

  static class CustomMethodMatcher implements MethodMatcher {

    private static final String[] PATTERNS = { "save" };

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
      boolean result = PatternMatchUtils.simpleMatch(PATTERNS, method.getName());
      log.info("pointcut result = {}", result);
      log.info("pointcut class = {}, pointcut method = {}", targetClass, method.getName());
      return result;
    }

    @Override
    public boolean isRuntime() {
      return false;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass, Object... args) {
      return false;
    }
  }
}

/*
addAdvice 로 Advice 넣을 시
DefaultPointcutAdvisor 를 통해 Pointcut.TRUE 로 들어가 모든 메서드에 적용
 */