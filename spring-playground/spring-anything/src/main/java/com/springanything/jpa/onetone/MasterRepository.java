package com.springanything.jpa.onetone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterRepository extends JpaRepository<Master, Long>, MasterRepositoryCustom {

}
