package com.example.springcorebasic.order.adapter.out.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.example.springcorebasic.member.domain.Member;
import com.example.springcorebasic.order.domain.Order;

public class MemoryLoadOrderRepository {

	private static final Map<Long, Order> STORE = new ConcurrentHashMap<>();

	public Order save(Member member, String itemName, int price, int discountPrice) {
		Long lastOrderId = STORE.keySet().stream()
			.max(Long::compareTo)
			.orElseGet(() -> 1L);

		Order order = Order.builder()
			.id(++lastOrderId)
			.memberId(member.getId())
			.itemName(itemName)
			.itemPrice(price)
			.discountPrice(discountPrice)
			.build();

		STORE.put(order.getId(), order);
		return order;
	}

	public Optional<Order> findById(Long orderId) {
		Order order = STORE.get(orderId);
		return Optional.ofNullable(order);
	}
}
