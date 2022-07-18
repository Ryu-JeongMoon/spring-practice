package com.springanything.mapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListMappingController {

	@GetMapping("/test/list-mapping")
	public ListMappingRequest mapping(@ModelAttribute ListMappingRequest listMappingRequest) {
		return listMappingRequest;
	}

}
