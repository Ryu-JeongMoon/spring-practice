package com.springanything.jpa.wrapper;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

interface HiddenRepository extends JpaRepository<InnerDomain, UUID> {

}
