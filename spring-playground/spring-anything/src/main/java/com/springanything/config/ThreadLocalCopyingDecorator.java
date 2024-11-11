package com.springanything.config;

import jakarta.annotation.Nonnull;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.task.TaskDecorator;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class ThreadLocalCopyingDecorator implements TaskDecorator {

  @Override
  @Nonnull
  public Runnable decorate(@Nonnull Runnable runnable) {
    LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
    SecurityContext securityContext = SecurityContextHolder.getContext();

    return () -> {
      try {
        LocaleContextHolder.setLocaleContext(localeContext, true);
        SecurityContextHolder.setContext(securityContext);
        runnable.run();
      } finally {
        LocaleContextHolder.resetLocaleContext();
        SecurityContextHolder.clearContext();
      }
    };
  }
}
