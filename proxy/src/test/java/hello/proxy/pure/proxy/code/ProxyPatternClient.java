package hello.proxy.pure.proxy.code;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProxyPatternClient {

  private final Subject subject;

  public void execute() {
    subject.operate();
  }
}
