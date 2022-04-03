package hello.proxy.pure.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ExecutionTimeLoggingComponent implements Component {

  private final Component target;

  @Override
  public String operate() {
    log.info("ExecutionTimeLoggingComponent Called");

    long startTime = System.currentTimeMillis();
    String result = target.operate();
    long endTime = System.currentTimeMillis();

    log.info("Execution Time = {}", (endTime - startTime));
    return result;
  }
}
