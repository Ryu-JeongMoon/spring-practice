package com.example.springcorebasic;

import com.example.springcorebasic.member.adapter.out.presistence.MemberPersistenceAdapter;
import com.example.springcorebasic.member.adapter.out.presistence.MemoryLoadMemberRepository;
import com.example.springcorebasic.member.application.service.ManageMemberService;
import com.example.springcorebasic.member.domain.Grade;
import com.example.springcorebasic.member.domain.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberApp {

	public static void main(String[] args) {
		MemoryLoadMemberRepository memoryLoadMemberRepository = new MemoryLoadMemberRepository();
		MemberPersistenceAdapter memberPersistenceAdapter = new MemberPersistenceAdapter(memoryLoadMemberRepository);
		ManageMemberService manageMemberService = new ManageMemberService(memberPersistenceAdapter);

		Member panda = new Member(1L, "panda", Grade.BASIC);
		manageMemberService.join(panda);

		Member findResult = manageMemberService.findMember(panda.getId());

		log.info("panda = {}", panda);
		log.info("findResult = {}", findResult);
		log.info("(panda == findResult) = {}", (panda == findResult));
	}
}

/*
객체지향 설계 방법에서의 핵심은 역할과 구현의 분리다
역할을 표현하는 인터페이스 기반으로 프로그램을 작성하고
구현을 담당하는 구체 클래스는 플러그인처럼 언제든지 바꿔도 프로그램 전체의 영향이 가지 않도록 할 수 있다
또한 역할을 사용해 작성하기 때문에 독립적인 개발이 가능해진다

다만 역할에 구현을 할당할 때, 즉 구체 클래스를 생성하여 의존성을 주입해주는 과정이 필요한데
생성을 담당하는 컴포넌트가 필요하며 일반적으로 제일 바깥 계층에서 모든 것에 의존하는 클래스가 된다
 */