package com.example.springcorebasic.member.application.port.in;

import com.example.springcorebasic.member.domain.Member;

public interface ManageMemberUseCase {

	void join(Member member);

	Member findMember(Long memberId);
}
