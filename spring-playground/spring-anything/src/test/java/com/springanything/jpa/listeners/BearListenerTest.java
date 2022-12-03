package com.springanything.jpa.listeners;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.springanything.SpringAnythingApplication;
import com.springanything.jpa.Bear;
import com.springanything.jpa.BearRepository;
import com.springanything.jpa.Panda;
import com.springanything.jpa.PandaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(classes = SpringAnythingApplication.class)
@Transactional
class BearListenerTest {

	@Autowired
	private EntityManager em;
	@Autowired
	private BearRepository bearRepository;
	@Autowired
	private PandaRepository pandaRepository;

	private Panda panda;
	private Bear bear;

	@BeforeEach
	void setUp() {
		Bear entity = Bear.builder()
			.region("서울")
			.build();
		bear = bearRepository.save(entity);

		Panda pandaEntity = Panda.builder()
			.name("판다")
			.age(10)
			.bearId(bear.getId())
			.build();
		panda = pandaRepository.save(pandaEntity);
	}

	@DisplayName("삭제..")
	@Test
	void delete() {
		// given
		bearRepository.delete(bear);

		// when
		em.flush();
		em.clear();

		// then
		Bear resultBear = bearRepository.findById(bear.getId())
			.orElse(null);

		Panda resultPanda = pandaRepository.findById(panda.getId())
			.orElse(null);

		log.info("resultBamboo:{}", resultBear);
		log.info("resultPanda:{}", resultPanda);
	}
}