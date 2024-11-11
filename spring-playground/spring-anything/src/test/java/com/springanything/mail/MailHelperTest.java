package com.springanything.mail;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import jakarta.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractIntegrationTest;

class MailHelperTest extends AbstractIntegrationTest {

  @Autowired
  private MailHelper mailHelper;

  @Test
  void send() throws MessagingException, UnsupportedEncodingException {
    mailHelper.send(
      "가입을 환영합니다",
      "rjm930302@gmail.com",
      "/mail-template/welcome",
      Map.of("name", "pandabears")
    );
  }
}
