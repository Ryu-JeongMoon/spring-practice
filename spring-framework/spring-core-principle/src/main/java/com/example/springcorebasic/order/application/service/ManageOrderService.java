package com.example.springcorebasic.order.application.service;

import java.util.Optional;

import com.example.springcorebasic.common.UseCase;
import com.example.springcorebasic.member.application.port.out.LoadMemberPort;
import com.example.springcorebasic.member.domain.Member;
import com.example.springcorebasic.order.application.port.in.ManageOrderUseCase;
import com.example.springcorebasic.order.application.port.out.LoadOrderPort;
import com.example.springcorebasic.order.domain.Order;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ManageOrderService implements ManageOrderUseCase {

	private final LoadOrderPort loadOrderPort;
	private final DiscountPolicy discountPolicy;
	private final LoadMemberPort loadMemberPort;

	@Override
	public Order order(Long memberId, String itemName, int price) {
		Member member = loadMemberPort.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("not exists member id - " + memberId));

		int discountAmount = discountPolicy.applyDiscount(member, price);
		return loadOrderPort.order(member, itemName, price, discountAmount);
	}

	@Override
	public Optional<Order> findById(Long orderId) {
		return loadOrderPort.findById(orderId);
	}
}
