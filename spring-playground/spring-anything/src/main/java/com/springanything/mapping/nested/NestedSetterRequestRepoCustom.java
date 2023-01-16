package com.springanything.mapping.nested;

import java.util.Optional;

public interface NestedSetterRequestRepoCustom {

	Optional<NestedSetterRequest> findByInnerName(String name);
}
