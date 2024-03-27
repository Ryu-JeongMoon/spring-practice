package com.springanything.basic;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class PasswordHashTest {

  @Test
  void hash() {
    // given
    Argon2PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    // when
    String[] splitted = passwordEncoder.encode("password").split("\\$");
    log.info("splitted = {}", Arrays.toString(splitted));

    String algorithm = splitted[1];
    String version = splitted[2];
    String arguments = splitted[3]; // arguments = memory, iterations, parallelism
    String salt = splitted[4];
    String hash = splitted[5];

    // then
    assertThat(algorithm).isNotBlank();
    assertThat(version).isNotBlank();
    assertThat(arguments).isNotBlank();
    assertThat(salt).isNotBlank();
    assertThat(hash).isNotBlank();
  }
}

/*
$argon2id$v=19$m=16384,t=2,p=1$hDn4P1qXsGHzPMGgRR3JDA$Fr1UANIjbg3Y0RGmXOg9yA8+Z4c1/2WF5ZUU+3mJnEQ
argon2id, algorithm
v=19, version
m=16384, memory
t=2, iterations
p=1, parallelism
hDn4P1qXsGHzPMGgRR3JDA, salt
Fr1UANIjbg3Y0RGmXOg9yA8+Z4c1/2WF5ZUU+3mJnEQ, hash
*/
