package com.example.inflearnbatch.domain.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inflearnbatch.domain.Panda;

public interface PandaRepository extends JpaRepository<Panda, Long> {

}
