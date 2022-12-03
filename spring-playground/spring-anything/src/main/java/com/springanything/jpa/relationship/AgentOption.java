package com.springanything.jpa.relationship;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "agent_option",
	indexes = @Index(name = "name_idx", columnList = "name")
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@ToString
public class AgentOption {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@ColumnDefault("0")
	private Integer age;

	@Builder
	private AgentOption(Long id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public void change(String name, Integer age) {
		this.name = name;
		this.age = age;
	}
}
