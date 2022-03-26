package org.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.time.Duration;
import org.junit.domain.Study;
import org.junit.domain.StudyStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StudyTest {

  @Test
  @DisplayName("스터디 생성")
  void createStudy() {
    Study study = new Study();

    assertAll(
      () -> assertNotNull(study),
      () -> assertEquals(study.getStatus(), StudyStatus.DRAFT,
        () -> "생성 시 상태는 <%s> 여야 한다".formatted(StudyStatus.DRAFT)),
      () -> assertEquals(study.getLimitCount(), 0,
        () -> "참석 인원은 <%d> 로 초기화 된다".formatted(0))
    );
  }

  @Test
  @DisplayName("타임아웃 걸어버리기")
  void createStudyWithTimeout() {
    assertTimeout(Duration.ofMillis(100),
      () -> {
        System.out.println("StudyTest.createStudyWithTimeout");
        Thread.sleep(96);
      });
  }

  @Test
  @DisplayName("AssertJ와 함께하는 테스트")
  void assertStatementWithAssertJ() {
    assertThat(new Study().getLimitCount()).isEqualTo(0);
  }
}

/*
BeforeAll, AfterAll -> static 이어야 함
assert 시리즈에 추가로 넘길 수 있는 String, StringSupplier
쓰잘데기 없는 연산이 일어나지 않는 지연 평가를 위해 StringSupplier 쓰는 것이 좋다

assertAll 묶어버리기, 묶으면 뭐가 좋길래?
여러 assert 시리즈를 꺼내놓고 돌리는 경우 첫번째 것이 터지면 그 뒤에는 수행이 안 된다
묶어 놓은 경우엔 일단 다 돌려버리고 뭐가 터졌는지 알려준다

assertTimeout 경과 시간 확인 가능, 초과한 경우 얼마나 초과했는지도 알려줌!?
단 얘는 동기적으로 실행되기 땜시 타임아웃 걸어놓은 시간이 초과하더라도 코드 블록이 끝날 때까지 기다린다
그 대안으로 assertTimeoutPreemptively 사용 가능하지만 Thread 관련 작업을 할 때 주의해야 한다

왜 주의해야 하는가?
1. 테스트도 별도의 스레드를 이용해 돌리기 때문에 오버헤드가 발생하고 이로 인해 제대로 된 측정이 안 될 수도 있고
2. @Transactional 걸어서 수행하는 테스트의 경우에 롤백이 되지 않아 테스트 결과가 DB에 반영될 수 있는 위험이 존재한다

assertThat 사용하면 서술적인 표현 가능
 */