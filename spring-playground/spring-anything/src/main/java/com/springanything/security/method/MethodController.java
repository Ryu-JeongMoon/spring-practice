package com.springanything.security.method;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MethodController {

  private final MethodUserRepository methodUserRepository;

  @PostMapping("/method-security/users")
  public MethodUser save(MethodUser methodUser) {
    return methodUserRepository.save(methodUser);
  }

  @GetMapping("/method-security/users")
  @MethodSecurityConfig.UserSelfAuthorize
  public MethodUser find(
    HttpServletRequest servletRequest,
    HttpServletResponse servletResponse,
    @Valid @ModelAttribute MethodUserApiRequest methodUserApiRequest
  ) {
    return methodUserRepository.findById(methodUserApiRequest.userId()).orElseThrow();
  }

  @GetMapping("/method-security/users/{userId}")
  @MethodSecurityConfig.UserSelfAuthorize
  public MethodUser findOne(
    @UserIdParameter
    @PathVariable
    String userId
  ) {
    return methodUserRepository.findById(userId).orElseThrow();
  }
}
