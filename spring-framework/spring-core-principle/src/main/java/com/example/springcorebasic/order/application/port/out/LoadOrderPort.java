package com.example.springcorebasic.order.application.port.out;

import java.util.Optional;

import com.example.springcorebasic.member.domain.Member;
import com.example.springcorebasic.order.domain.Order;

public interface LoadOrderPort {

	Order order(Member member, String itemName, int price, int discountPrice);

	Optional<Order> findById(Long orderId);
}
