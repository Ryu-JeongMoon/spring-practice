package com.springanything.mvc.acceptlanguage;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AcceptLanguageController {

  @GetMapping("/accept-language")
  public String acceptLanguage(
    @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) String acceptLanguage
  ) {
    log.info("acceptLanguage: {}", acceptLanguage);
    log.info("locale: {}", LocaleContextHolder.getLocale());

    return LocaleContextHolder.getLocale().toLanguageTag();
  }
}
