package org.junit.sutdy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.member.MemberService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

  @Mock
  MemberService memberService;
  @Mock
  StudyRepository studyRepository;
  @InjectMocks
  StudyService studyService;

  @Test
  @DisplayName("의존성 주입해버리기 - 1")
  void createStudyServiceWithMockitoApi() {
    MemberService memberService = Mockito.mock(MemberService.class);
    StudyRepository studyRepository = Mockito.mock(StudyRepository.class);

    StudyService studyService = new StudyService(memberService, studyRepository);
    assertNotNull(studyService);
  }

  @Test
  @DisplayName("의존성 주입해버리기 - 2")
  void createStudyServiceWithExtension() {
    StudyService studyService = new StudyService(memberService, studyRepository);
    assertNotNull(studyService);
  }

  @Test
  @DisplayName("지정해버리기")
  void stubbingWithMember() {
    Member member = Member.builder()
      .id(1L)
      .email("panda")
      .build();

    when(memberService.findById(anyLong())).thenReturn(Optional.of(member));

    Optional<Member> optionalMember = memberService.findById(999L);
    assertEquals(optionalMember.get(), member);
  }

  @Test
  @DisplayName("다중 스터빙?!")
  void stubbingMultiply() {
    Member member = Member.builder()
      .id(1L)
      .email("panda")
      .build();

    when(memberService.findById(any()))
      .thenReturn(Optional.of(member))
      .thenReturn(Optional.empty())
      .thenThrow(new IllegalArgumentException());

    Optional<Member> member1 = memberService.findById(1L);
    assertEquals(member1.get(), member);

    Optional<Member> member2 = memberService.findById(2L);
    assertEquals(member2, Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> memberService.findById(3L));
  }
}

/*
@Mock, @MockBean 등 애노테이션 사용하기 위해서
@ExtendWith(MockitoExtension.class) Extension 명시해줘야 함
 */