package com.example.springcorebasic.member.application.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.springcorebasic.member.adapter.out.presistence.MemberPersistenceAdapter;
import com.example.springcorebasic.member.adapter.out.presistence.MemoryLoadMemberRepository;
import com.example.springcorebasic.member.domain.Grade;
import com.example.springcorebasic.member.domain.Member;

class ManageMemberServiceTest {

	private final MemoryLoadMemberRepository memoryLoadMemberRepository = new MemoryLoadMemberRepository();
	private final MemberPersistenceAdapter memberPersistenceAdapter = new MemberPersistenceAdapter(memoryLoadMemberRepository);
	private final ManageMemberService manageMemberService = new ManageMemberService(memberPersistenceAdapter);

	@Test
	@DisplayName("가입 확인")
	void joinAndFind() {
		// given
		Member panda = new Member(1L, "panda", Grade.VIP);

		// when
		manageMemberService.join(panda);

		// then
		Member findResult = manageMemberService.findMember(panda.getId());
		assertThat(findResult).isEqualTo(panda);
	}
}