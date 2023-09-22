package com.springanything.mapping.nested;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NestedSetterRequestRepo extends JpaRepository<NestedSetterRequest, String>, NestedSetterRequestRepoCustom {

}
