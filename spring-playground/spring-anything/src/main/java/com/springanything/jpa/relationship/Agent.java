package com.springanything.jpa.relationship;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Agent {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "option_id", updatable = false, nullable = false)
	@ToString.Exclude
	private AgentOption option;

	@Builder(access = AccessLevel.PRIVATE)
	private Agent(Long id, AgentOption option) {
		this.id = id;
		this.option = option;
	}

	public static Agent from(AgentOption option) {
		return Agent.builder()
			.option(option)
			.build();
	}
}
