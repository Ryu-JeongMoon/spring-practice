package org.springaop.proxytype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springaop.member.MemberService;
import org.springaop.member.MemberServiceImpl;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyTargetTest {

  @Test
  @DisplayName("인터페이스 기반 생성 - 구체 클래스로 캐스팅 불가")
  void jdkDynamicProxy() {
    MemberServiceImpl target = new MemberServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(false);

    MemberService proxy = (MemberService) proxyFactory.getProxy();

    Assertions.assertThrows(ClassCastException.class, () -> {
      MemberServiceImpl proxy2 = (MemberServiceImpl) proxyFactory.getProxy();
    });
  }

  @Test
  @DisplayName("상속 기반 생성 - 구체 클래스 캐스팅 가능")
  void cglibProxy() {
    MemberServiceImpl target = new MemberServiceImpl();
    ProxyFactory proxyFactory = new ProxyFactory(target);
    proxyFactory.setProxyTargetClass(true);

    MemberService proxy = (MemberService) proxyFactory.getProxy();
    MemberServiceImpl proxy2 = (MemberServiceImpl) proxyFactory.getProxy();
  }
}
