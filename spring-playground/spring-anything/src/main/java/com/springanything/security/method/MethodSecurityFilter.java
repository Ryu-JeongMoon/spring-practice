package com.springanything.security.method;

import java.io.IOException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MethodSecurityFilter extends OncePerRequestFilter {

  private final MethodUserRepository methodUserRepository;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return !PatternMatchUtils.simpleMatch("/method-security/**", request.getRequestURI())
      || StringUtils.equals(HttpMethod.POST.name(), request.getMethod());
  }

  @Override
  protected void doFilterInternal(
    @Nonnull HttpServletRequest request,
    @Nonnull HttpServletResponse response,
    @Nonnull FilterChain filterChain
  ) throws ServletException, IOException {

    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isNotBlank(authorization)) {
      methodUserRepository.findById(authorization)
        .ifPresent(SecurityUtils::setAuthentication);
    }

    filterChain.doFilter(request, response);
  }
}
