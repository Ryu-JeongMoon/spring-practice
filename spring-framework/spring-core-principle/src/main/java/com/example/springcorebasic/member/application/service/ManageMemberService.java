package com.example.springcorebasic.member.application.service;

import com.example.springcorebasic.common.UseCase;
import com.example.springcorebasic.member.application.port.in.ManageMemberUseCase;
import com.example.springcorebasic.member.application.port.out.LoadMemberPort;
import com.example.springcorebasic.member.domain.Member;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ManageMemberService implements ManageMemberUseCase {

	private final LoadMemberPort loadMemberPort;

	@Override
	public void join(Member member) {
		loadMemberPort.save(member);
	}

	@Override
	public Member findMember(Long memberId) {
		return loadMemberPort.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("not exists memberId - " + memberId));
	}
}
