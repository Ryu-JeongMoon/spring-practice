package com.springanything.security.method;

import jakarta.validation.constraints.NotBlank;

public record MethodUserApiRequest(
  @UserIdParameter
  @NotBlank
  String userId
) implements ApiRequest {

}
