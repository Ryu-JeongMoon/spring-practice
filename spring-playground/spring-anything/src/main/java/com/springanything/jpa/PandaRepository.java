package com.springanything.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PandaRepository extends JpaRepository<Panda, Long>, PandaRepositoryCustom {

  List<Panda> findByBambooId(Long bambooId);
}
