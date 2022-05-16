package com.springservletthymeleaf.basic.itemservice.domain.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

	@Id
	@GeneratedValue
	@Column(name = "item_id")
	@EqualsAndHashCode.Include
	private Long id;

	@EqualsAndHashCode.Include
	private String name;

	@EqualsAndHashCode.Include
	private int price;

	@EqualsAndHashCode.Include
	private int quantity;

	@Builder
	public Item(String name, int price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
}
