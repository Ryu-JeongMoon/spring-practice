package com.example.springcorebasic.order.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

	private Long id;
	private Long memberId;
	private String itemName;
	private int itemPrice;
	private int discountPrice;

	public int discount() {
		if (itemPrice < discountPrice)
			throw new IllegalArgumentException("discount price should be smaller than item price");

		return itemPrice - discountPrice;
	}
}
