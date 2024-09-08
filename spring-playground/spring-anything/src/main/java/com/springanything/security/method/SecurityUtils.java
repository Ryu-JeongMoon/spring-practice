package com.springanything.security.method;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

  public static String currentUserId() {
    Object principal = getAuthentication().getPrincipal();
    if (principal instanceof MethodUser user) {
      return user.getId();
    }
    throw new IllegalArgumentException("Principal not found");
  }

  private static Authentication getAuthentication() {
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    if (authentication == null) {
      throw new IllegalStateException("Authentication not found");
    }
    return authentication;
  }

  public static void setAuthentication(MethodUser methodUser) {
    List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(methodUser.getAuthorities());
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(methodUser, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
