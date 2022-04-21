package org.springkafka.interactor;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;
import org.springkafka.model.Panda;

@Component
public class PandaPublisher {

	private static final SecureRandom random = new SecureRandom();

	private final AtomicInteger id = new AtomicInteger();

	public Panda getRandomPanda() {
		Panda.BirthPlace[] birthPlaces = Panda.BirthPlace.values();
		int randomIndex = random.nextInt(0, birthPlaces.length - 1);

		int id = this.id.incrementAndGet();
		return Panda.builder()
			.id(id)
			.name("#" + id)
			.birthPlace(birthPlaces[randomIndex])
			.build();
	}
}