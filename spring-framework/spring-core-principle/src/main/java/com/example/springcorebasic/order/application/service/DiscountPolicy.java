package com.example.springcorebasic.order.application.service;

import com.example.springcorebasic.member.domain.Member;

public interface DiscountPolicy {

	int applyDiscount(Member member, int price);
}
