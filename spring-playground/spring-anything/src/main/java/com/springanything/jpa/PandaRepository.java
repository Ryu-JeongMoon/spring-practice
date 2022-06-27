package com.springanything.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PandaRepository extends JpaRepository<Panda, Long>, PandaRepositoryCustom {

}
