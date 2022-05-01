package com.example.springcorebasic.member.adapter.out.presistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.example.springcorebasic.member.domain.Member;

public class MemoryLoadMemberRepository {

	private static final Map<Long, Member> STORE = new ConcurrentHashMap<>();

	void save(Member member) {
		STORE.put(member.getId(), member);
	}

	Optional<Member> findById(Long memberId) {
		Member member = STORE.get(memberId);
		return Optional.ofNullable(member);
	}
}
