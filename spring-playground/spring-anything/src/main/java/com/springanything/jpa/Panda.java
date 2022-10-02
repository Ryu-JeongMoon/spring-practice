package com.springanything.jpa;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.springanything.jpa.base.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString
public class Panda extends BaseEntity<Long> {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	protected String name;

	protected int age;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "bamboo_id")
	@ToString.Exclude
	protected Bamboo bamboo;

	@Transient
	@ToString.Exclude
	protected int weight;

	@Column(name = "bear_id")
	private Long bearId;

	@Builder
	public Panda(String name, int age, Bamboo bamboo, Long bearId, int weight) {
		this.name = name;
		this.age = age;
		this.bamboo = bamboo;
		this.bearId = bearId;
		this.weight = weight;
	}

	public String getType() {
		return bamboo.getType();
	}

	@Transient
	public int getDoubleAge() {
		return age * 2;
	}

	@Transient
	public int getWeight() throws NoSuchAlgorithmException {
		return SecureRandom.getInstanceStrong().nextInt();
	}
}
