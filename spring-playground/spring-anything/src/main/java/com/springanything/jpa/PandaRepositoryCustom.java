package com.springanything.jpa;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SharedSessionContract;

public interface PandaRepositoryCustom {

	Optional<PandaResponse> findType(Long id);

	Optional<Panda> findPandaType(Long id);

	Optional<PandaChild> findChild(Long id);

	void deleteByBearId(Long bearId, SharedSessionContract session);
}
