package com.springanything.jpa.cascade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Audited
@Getter
public class Flag {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@Builder
	public Flag(Long id, String name, Team team) {
		this.id = id;
		this.name = name;
		this.team = team;
	}
}
