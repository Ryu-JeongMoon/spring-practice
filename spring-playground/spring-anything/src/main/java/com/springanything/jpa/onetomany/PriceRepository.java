package com.springanything.jpa.onetomany;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<PandaPrice, String> {

}
