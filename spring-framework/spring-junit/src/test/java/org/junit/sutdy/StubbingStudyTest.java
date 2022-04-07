package org.junit.sutdy;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.domain.Member;
import org.junit.domain.Study;
import org.junit.domain.StudyStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.member.MemberService;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StubbingStudyTest {

  @Mock
  MemberService memberService;
  @Mock
  StudyRepository studyRepository;

  private Study getStudy(long id) {
    return Study.builder()
      .id(id)
      .name("JAVA")
      .ownerId(id)
      .limitCount(5)
      .status(StudyStatus.OPENED)
      .openedDateTime(LocalDateTime.now())
      .build();
  }

  private Member getMember(long id) {
    return Member.builder()
      .id(id)
      .email("panda")
      .build();
  }

  @Test
  @DisplayName("연습 문제 - 1")
  void stubbingExample() {
    StudyService studyService = new StudyService(memberService, studyRepository);

    Member member = getMember(1L);
    Study study = getStudy(1L);

    given(memberService.findById(anyLong())).willReturn(Optional.of(member));
    given(studyRepository.save(any(Study.class))).willReturn(study);

    Study newStudy = studyService.createNewStudy(5L, study);

    assertAll(
      () -> assertNotNull(newStudy),
      () -> assertEquals(newStudy, study),
      () -> assertEquals(newStudy.getOwnerId(), study.getOwnerId()),
      () -> verify(memberService, times(1)).notify(newStudy)
    );
  }

  @Test
  @DisplayName("BDD 사용하기")
  void doBDDWithMockito() {
    StudyService studyService = new StudyService(memberService, studyRepository);

    Member member = getMember(2L);
    Study study = getStudy(2L);

    given(studyRepository.save(study)).willReturn(study);
    given(memberService.findById(anyLong())).willReturn(Optional.of(member));

    Study newStudy = studyService.createNewStudy(member.getId(), study);

    assertAll(
      () -> assertNotNull(newStudy),
      () -> assertEquals(newStudy, study),
      () -> assertEquals(newStudy.getOwnerId(), study.getOwnerId()),
      () -> BDDMockito.then(memberService).should(times(1)).notify(newStudy),
      () -> BDDMockito.then(memberService).should(times(1)).findById(anyLong())
    );
  }

}

/*
Behavior Driven Development, BDD
when -> given
verify -> then
좀 더 표현력 있고 직관적인 API?!

따로 돌리면 돌아가는데 같이 돌리면 터지는 이건 무엇?!
필드에서 @InjectMocks 받았을 때 터졌고
각각의 테스트에서 StudyService 만들어서 사용하니 오께이
todo, 이 부분 더 알아볼 것
 */