package org.springkafka.interactor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springkafka.model.Panda;

class PandaPublisherTest {

	@Test
	@DisplayName("랜덤 팬더 생성")
	void getPandaRandomly() {
		PandaPublisher pandaPublisher = new PandaPublisher();

		Panda panda1 = pandaPublisher.getRandomPanda();
		Panda panda2 = pandaPublisher.getRandomPanda();

		Assertions.assertThat(panda1.getId()).isEqualTo(1);
		Assertions.assertThat(panda2.getId()).isEqualTo(2);
	}
}