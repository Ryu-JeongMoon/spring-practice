package com.springanything.jpa.insert;

import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import com.springanything.AbstractRepositoryTest;

class WangPandaRepoTest extends AbstractRepositoryTest {

	@Autowired
	private WangPandaRepo wangPandaRepo;
	@Autowired
	private KingPandaRepo kingPandaRepo;

	// 691645000 ns, 658579625 ns, 438133417 ns, 368901375 ns, 466651542 ns, 465444500 ns, 373084333 ns
	@RepeatedTest(3)
	void wang() {
		// given
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		// when
		for (int i = 0; i < 10_000; i++) {
			WangPanda wangPanda = WangPanda.builder()
				.name("wang")
				.build();

			wangPandaRepo.save(wangPanda);
		}

		// then
		entityManager.flush();
		entityManager.clear();

		stopWatch.stop();
		log.info("elapsed time: {}", stopWatch.prettyPrint());
	}

	// 580429583 ns, 577250750 ns, 512133250 ns, 377041791 ns, 620760208 ns, 595315625 ns, 400959166 ns
	@RepeatedTest(3)
	void king() {
		// given
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		// when
		for (int i = 0; i < 10_000; i++) {
			KingPanda wangPanda = KingPanda.builder()
				.name("wang")
				.build();

			kingPandaRepo.save(wangPanda);
		}

		// then
		entityManager.flush();
		entityManager.clear();

		stopWatch.stop();
		log.info("elapsed time: {}", stopWatch.prettyPrint());
	}
}