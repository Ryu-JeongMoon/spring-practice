package com.springanything.jpa;

import java.util.Optional;

public interface PandaRepositoryCustom {

	Optional<PandaResponse> findType(Long id);

	Optional<Panda> findPandaType(Long id);

	Optional<PandaChild> findChild(Long id);
}
