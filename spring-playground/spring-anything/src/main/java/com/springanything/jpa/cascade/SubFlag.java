package com.springanything.jpa.cascade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class SubFlag {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Builder
	public SubFlag(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
