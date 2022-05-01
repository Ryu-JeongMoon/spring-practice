package com.example.springcorebasic.order.application.port.in;

import java.util.Optional;

import com.example.springcorebasic.order.domain.Order;

public interface ManageOrderUseCase {

	Order order(Long memberId, String itemName, int price);

	Optional<Order> findById(Long orderId);
}
