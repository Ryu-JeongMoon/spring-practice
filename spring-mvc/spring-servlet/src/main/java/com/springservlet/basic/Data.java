package com.springservlet.basic;

import lombok.Builder;

@Builder
public record Data(String username, int age) {

}
