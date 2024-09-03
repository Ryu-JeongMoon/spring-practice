package com.springanything.http.etag;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EtagRepository extends JpaRepository<EtagEntity, String> {

  Optional<EtagEntity> findByName(String name);
}
