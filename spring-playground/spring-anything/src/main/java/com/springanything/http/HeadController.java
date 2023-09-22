package com.springanything.http;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HeadController {

  @GetMapping("/test/head")
  public String head() {
    log.info("HEAD called");
    return "head";
  }
}

/*
HEAD -> GET과 동일하게 동작하지만 응답 본문을 받지 않음, 로직 타긴 하나 응답이 안 가는 것
OPTIONS -> 서버가 어떤 메서드를 지원하는지 확인하는 용도로 사용, 로직 타지 않음

- REQUEST
HEAD http://localhost:8080/test/head

- RESPONSE
HTTP/1.1 200
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Security-Policy: script-src 'self
Content-Type: text/plain;charset=UTF-8
Content-Length: 4
Date: Sun, 04 Dec 2022 11:03:28 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>

- REQUEST
OPTIONS http://localhost:8080/test/head

- RESPONSE
HTTP/1.1 200
Allow: GET,HEAD,OPTIONS
Accept-Patch:
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Security-Policy: script-src 'self
Content-Length: 0
Date: Sun, 04 Dec 2022 11:04:34 GMT
Keep-Alive: timeout=60
Connection: keep-alive

<Response body is empty>
 */
