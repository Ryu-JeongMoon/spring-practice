package com.springanything.jpa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PandaController {

	private final PandaRepository pandaRepository;

	@GetMapping("/panda")
	public Panda panda() {
		return pandaRepository.findPandaType(1L).orElseThrow();
	}

	@GetMapping("/panda-response")
	public PandaResponse pandaResponse() {
		return pandaRepository.findType(1L).orElseThrow();
	}

	@GetMapping("/panda-child")
	public PandaChild pandaChild() {
		return pandaRepository.findChild(1L).orElseThrow();
	}
}
