package org.securitydemo.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountRepository.findByUsername(username)
      .orElseThrow(() -> new UsernameNotFoundException(username));

    return User.builder()
      .username(account.getUsername())
      .password(account.getPassword())
      .roles(account.getRole().toString())
      .build();
  }

  @Transactional
  public Account create(Account account) {
    account.encodePasswordBy(passwordEncoder);
    return accountRepository.save(account);
  }
}
