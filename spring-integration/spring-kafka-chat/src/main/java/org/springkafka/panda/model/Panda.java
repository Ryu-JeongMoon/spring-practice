package org.springkafka.panda.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Panda {

	private int id;
	private String name;
	private BirthPlace birthPlace;

	public enum BirthPlace {
		ASIA, AMERICA, EUROPE, AFRICA, OCEANIA
	}
}
