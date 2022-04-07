package hello.proxy.pure.proxy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ProxyCacheSubject implements Subject {

  private static String resultCache;

  private final Subject target;

  @Override
  public String operate() {
    log.info("Proxy Called");

    if (resultCache == null)
      resultCache = target.operate();

    return resultCache;
  }
}
