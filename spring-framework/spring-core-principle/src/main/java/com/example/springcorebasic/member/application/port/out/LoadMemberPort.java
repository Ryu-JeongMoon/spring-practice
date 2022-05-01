package com.example.springcorebasic.member.application.port.out;

import java.util.Optional;

import com.example.springcorebasic.member.domain.Member;

public interface LoadMemberPort {

	void save(Member member);

	Optional<Member> findById(Long memberId);
}
