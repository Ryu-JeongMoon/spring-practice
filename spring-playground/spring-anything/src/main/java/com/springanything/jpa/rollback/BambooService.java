package com.springanything.jpa.rollback;

import org.springframework.stereotype.Service;

import com.springanything.jpa.BambooRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BambooService {

	private final BambooRepository bambooRepository;

	public void deleteById(Long id) {
		try {
			log.info("bambooRepository.deleteById({}) request", id);
			bambooRepository.deleteById(id);
		} catch (RuntimeException e) {
			log.error("bambooRepository.deleteById({}) error", id);
			bambooRepository.deleteById(id + 1);
			log.info("bambooRepository.deleteById({}) processed", id + 1);
		}
	}
}
