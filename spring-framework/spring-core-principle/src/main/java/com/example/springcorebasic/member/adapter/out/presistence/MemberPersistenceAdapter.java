package com.example.springcorebasic.member.adapter.out.presistence;

import java.util.Optional;

import com.example.springcorebasic.common.PersistenceAdapter;
import com.example.springcorebasic.member.application.port.out.LoadMemberPort;
import com.example.springcorebasic.member.domain.Member;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements LoadMemberPort {

	private final MemoryLoadMemberRepository memoryLoadMemberRepository;

	@Override
	public void save(Member member) {
		memoryLoadMemberRepository.save(member);
	}

	@Override
	public Optional<Member> findById(Long memberId) {
		return memoryLoadMemberRepository.findById(memberId);
	}
}
