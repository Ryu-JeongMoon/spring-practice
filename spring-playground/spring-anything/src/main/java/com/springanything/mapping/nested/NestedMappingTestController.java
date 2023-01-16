package com.springanything.mapping.nested;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NestedMappingTestController {

	private final NestedSetterService nestedSetterService;

	@GetMapping(value = "/test/nested-setter/{id}")
	public NestedSetterRequest get(@PathVariable String id) {
		NestedSetterRequest request = nestedSetterService.get(id);
		log.info("request = {}", request);
		return request;
	}

	@GetMapping(value = "/test/nested-setter/name/{name}")
	public NestedSetterRequest findByInnerName(@PathVariable String name) {
		NestedSetterRequest request = nestedSetterService.findByInnerName(name);
		log.info("request = {}", request);
		return request;
	}

	@PostMapping(value = "/test/nested-setter")
	public NestedSetterRequest save(@ModelAttribute NestedSetterRequest request) {
		log.info("request = {}", request);
		return nestedSetterService.save(request);
	}

	@PatchMapping(value = "/test/nested-setter/{id}")
	public NestedSetterRequest patch(@PathVariable String id, @ModelAttribute NestedSetterRequest request) {
		log.info("request = {}", request);
		return nestedSetterService.patch(id, request);
	}
}
