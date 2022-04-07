package org.junit.member;

import java.util.Optional;
import org.junit.domain.Member;
import org.junit.domain.Study;

public interface MemberService {

  Optional<Member> findById(Long memberId);

  void validate(Long memberId);

  void notify(Study newstudy);

  void notify(Member member);
}
