package com.springanything.jpa;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Panda {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	protected String name;

	protected int age;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bear_id")
	protected Bear bear;

	@Transient
	protected int weight;

	public String getType() {
		return bear.getType();
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
