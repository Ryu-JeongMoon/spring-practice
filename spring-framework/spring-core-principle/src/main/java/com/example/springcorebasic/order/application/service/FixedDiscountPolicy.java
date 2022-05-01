package com.example.springcorebasic.order.application.service;

import com.example.springcorebasic.member.domain.Grade;
import com.example.springcorebasic.member.domain.Member;

public class FixedDiscountPolicy implements DiscountPolicy {

	private static final int FIXED_DISCOUNT_PRICE = 1_000;

	@Override
	public int applyDiscount(Member member, int price) {
		return member.getGrade() == Grade.VIP
			? FIXED_DISCOUNT_PRICE
			: 0;
	}
}
