package org.springaop.pointcut;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springaop.member.MemberServiceImpl;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

@Slf4j
public class ExecutionTest {

  AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
  Method helloMethod;

  @BeforeEach
  void setUp() throws NoSuchMethodException {
    helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
  }

  @Test
  @DisplayName("메서드 정보 출력")
  void print() {
    // public java.lang.String org.springaop.member.MemberServiceImpl.hello(java.lang.String)
    log.info("helloMethod = {}", helloMethod);
  }

  @Test
  @DisplayName("키워드 - 접근 제어자 - 반환 타입 - 패키지 - 메서드명 - 메서드 매개변수 형식")
  void exactMatch() {
    pointcut.setExpression("execution(public String org.springaop.member.MemberServiceImpl.hello(String))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  @DisplayName("메서드 이름만으로 매치")
  void nameMatch() {
    pointcut.setExpression("execution(* hello(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  @DisplayName("메서드 이름만으로 매치 - 전위")
  void prefixPatternMatch() {
    pointcut.setExpression("execution(* *lo(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  @DisplayName("메서드 이름만으로 매치 - 후위")
  void suffixPatternMatch() {
    pointcut.setExpression("execution(* he*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  @DisplayName("패키지 매치 - 클래스명")
  void packageMatchWithClass() {
    pointcut.setExpression("execution(* org.springaop.member.MemberServiceImpl.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  @DisplayName("패키지 매치 - 클래스명 생략")
  void packageMatch() {
    pointcut.setExpression("execution(* org.springaop.member.*.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }

  @Test
  @DisplayName("패키지 매치 실패")
  void packageMatchFailByHierarchy() {
    pointcut.setExpression("execution(* org.springaop.*.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
  }

  @Test
  @DisplayName("패키지 계층 매치 실패")
  void packageHierarchyMatch() {
    pointcut.setExpression("execution(* org.springaop..*.*(..))");
    assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
  }
}
