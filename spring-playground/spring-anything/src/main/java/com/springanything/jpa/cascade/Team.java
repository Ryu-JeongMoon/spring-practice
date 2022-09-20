package com.springanything.jpa.cascade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	// cascade=ALL is equivalent to cascade={PERSIST, MERGE, REMOVE, REFRESH, DETACH}.
	@OneToMany(
		mappedBy = "team",
		fetch = FetchType.LAZY,
		cascade = { CascadeType.PERSIST, CascadeType.MERGE},
		orphanRemoval = true
	)
	private List<Member> members = new ArrayList<>();

	public void addMember(Member member) {
		members.add(member);
		member.setTeam(this);
	}
}

