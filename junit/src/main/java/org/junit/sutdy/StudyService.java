package org.junit.sutdy;

import org.junit.domain.Member;
import org.junit.domain.Study;
import org.junit.member.MemberService;

public class StudyService {

  private final MemberService memberService;

  private final StudyRepository repository;

  public StudyService(MemberService memberService, StudyRepository repository) {
    assert memberService != null;
    assert repository != null;
    this.memberService = memberService;
    this.repository = repository;
  }

  public Study createNewStudy(Long memberId, Study study) {
    Member member = memberService.findById(memberId)
      .orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: '" + memberId + "'"));

    study.setOwnerId(member.getId());
    Study newStudy = repository.save(study);
    memberService.notify(newStudy);
    return newStudy;
  }

  public Study openStudy(Study study) {
    study.open();
    Study openedStudy = repository.save(study);
    memberService.notify(openedStudy);
    return openedStudy;
  }

  public void hi() {

  }
}
