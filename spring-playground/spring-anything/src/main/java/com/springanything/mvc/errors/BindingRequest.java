package com.springanything.mvc.errors;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

public record BindingRequest(
	@NotBlank
	String name,

	@Range(min = 1, max = 5)
	Integer age
) {

}
