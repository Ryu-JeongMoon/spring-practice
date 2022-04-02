package hello.advanced.template;

import hello.advanced.template.code.AbstractTemplate;
import hello.advanced.template.code.SubClassLogic1;
import hello.advanced.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

  @Test
  @DisplayName("걍 때려박기")
  void templateMethodV0() {
    logic1();
    logic2();
  }

  private void logic1() {
    long startTime = System.currentTimeMillis();

    // 비지니스 로직 시작
    log.info("비지니스 로직 실행 1");
    // 비지니스 로직 종료

    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("resultTime = {}", resultTime);
  }

  private void logic2() {
    long startTime = System.currentTimeMillis();

    // 비지니스 로직 시작
    log.info("비지니스 로직 실행 2");
    // 비지니스 로직 종료

    long endTime = System.currentTimeMillis();
    long resultTime = endTime - startTime;
    log.info("resultTime = {}", resultTime);
  }

  @Test
  @DisplayName("템플릿 메서드 패턴 활용")
  void templateMethodV1() {
    AbstractTemplate template1 = new SubClassLogic1();
    AbstractTemplate template2 = new SubClassLogic2();

    template1.execute();
    template2.execute();
  }

  @Test
  @DisplayName("익명 내부 클래스 활용")
  void templateMethodV2() {
    AbstractTemplate template1 = new AbstractTemplate() {

      @Override
      protected void call() {
        log.info("비지니스 로직 실행 1");
      }
    };

    AbstractTemplate template2 = new AbstractTemplate() {

      @Override
      protected void call() {
        log.info("비지니스 로직 실행 2");
      }
    };

    template1.execute();
    template2.execute();

    System.out.println("template1.getClass() = " + template1.getClass());
    System.out.println("template2.getClass() = " + template2.getClass());
  }
}
