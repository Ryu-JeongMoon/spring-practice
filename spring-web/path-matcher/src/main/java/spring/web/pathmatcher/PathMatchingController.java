package spring.web.pathmatcher;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import spring.web.pathmatcher.constant.Uris;

@RestController
@Slf4j
public class PathMatchingController {

  @GetMapping(value = {
    Uris.ROOT,
    Uris.LOCALE,
    Uris.LOCALE_SLASH
  })
  public String home(@PathVariable(required = false) String locale) {
    if (StringUtils.hasText(locale)) {
      log.info("locale = {}", locale);
    }
    return "home!";
  }

  @GetMapping(Uris.EXCEPT_HOME)
  public String exceptHome() {
    return "not home!";
  }
}
