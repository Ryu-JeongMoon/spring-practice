package com.springanything.jpa.enums;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class WhiteBear {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated
	@Column(name = "bear_option")
	private BearOption bearOption;

	public WhiteBear(BearOption bearOption) {
		this.bearOption = bearOption;
	}
}
