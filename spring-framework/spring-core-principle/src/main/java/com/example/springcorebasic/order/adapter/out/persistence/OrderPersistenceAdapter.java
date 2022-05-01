package com.example.springcorebasic.order.adapter.out.persistence;

import java.util.Optional;

import com.example.springcorebasic.common.PersistenceAdapter;
import com.example.springcorebasic.member.domain.Member;
import com.example.springcorebasic.order.application.port.out.LoadOrderPort;
import com.example.springcorebasic.order.domain.Order;

import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements LoadOrderPort {

	private final MemoryLoadOrderRepository memoryLoadOrderRepository;

	@Override
	public Order order(Member member, String itemName, int price, int discountAmount) {
		return memoryLoadOrderRepository.save(member, itemName, price, discountAmount);
	}

	@Override
	public Optional<Order> findById(Long orderId) {
		return memoryLoadOrderRepository.findById(orderId);
	}
}
