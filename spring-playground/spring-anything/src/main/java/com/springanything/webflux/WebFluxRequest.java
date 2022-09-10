package com.springanything.webflux;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WebFluxRequest(@JsonProperty("NumStart") int startCount, @JsonProperty("NumEnd") int endCount) {

}
