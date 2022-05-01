package com.example.springcorebasic.order.application.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.springcorebasic.member.adapter.out.presistence.MemberPersistenceAdapter;
import com.example.springcorebasic.member.adapter.out.presistence.MemoryLoadMemberRepository;
import com.example.springcorebasic.member.application.port.in.ManageMemberUseCase;
import com.example.springcorebasic.member.application.port.out.LoadMemberPort;
import com.example.springcorebasic.member.application.service.ManageMemberService;
import com.example.springcorebasic.member.domain.Grade;
import com.example.springcorebasic.member.domain.Member;
import com.example.springcorebasic.order.adapter.out.persistence.MemoryLoadOrderRepository;
import com.example.springcorebasic.order.adapter.out.persistence.OrderPersistenceAdapter;
import com.example.springcorebasic.order.application.port.in.ManageOrderUseCase;
import com.example.springcorebasic.order.application.port.out.LoadOrderPort;
import com.example.springcorebasic.order.domain.Order;

class ManageOrderServiceTest {

	MemoryLoadMemberRepository memberRepository = new MemoryLoadMemberRepository();
	LoadMemberPort loadMemberPort = new MemberPersistenceAdapter(memberRepository);

	MemoryLoadOrderRepository orderRepository = new MemoryLoadOrderRepository();
	LoadOrderPort loadOrderPort = new OrderPersistenceAdapter(orderRepository);
	DiscountPolicy discountPolicy = new FixedDiscountPolicy();

	ManageOrderUseCase orderUseCase = new ManageOrderService(loadOrderPort, discountPolicy, loadMemberPort);
	ManageMemberUseCase memberUseCase = new ManageMemberService(loadMemberPort);

	@Test
	@DisplayName("주문")
	void order() {
		// given
		Member panda = new Member(1L, "panda", Grade.VIP);
		memberUseCase.join(panda);
		Member findMember = memberUseCase.findMember(panda.getId());

		// when
		Order order = orderUseCase.order(findMember.getId(), "bear", 10000);

		// then
		assertThat(order.discount()).isEqualTo(9000);
	}
}