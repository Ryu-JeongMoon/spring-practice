package spring.web.pathmatcher.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Uris {

  public static final String ROOT = "/";
  public static final String LOCALE = "/{locale:ko|en|jp|uk}";
  public static final String LOCALE_SLASH = LOCALE + "/";

  public static final String EXCEPT_HOME = "/**";
}
