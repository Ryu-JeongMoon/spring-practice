package com.springanything.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.springanything.jpa.base.BaseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@ToString
public class Bear extends BaseEntity<Long> {

	@Id
	@GeneratedValue
	private Long id;

	private String region;

	@Builder
	public Bear(String region) {
		this.region = region;
	}
}
