package com.springanything.jpa;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BearService {

	private final BearRepository bearRepository;

	public void deleteById(Long bambooId) {
		bearRepository.deleteById(bambooId);
	}
}
