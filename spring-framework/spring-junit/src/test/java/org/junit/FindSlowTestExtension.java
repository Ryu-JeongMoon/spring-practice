package org.junit;

import java.lang.reflect.Method;
import org.junit.annotation.SlowTest;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

  public static final String START_TIME = "START_TIME";
  private final long threshold;

  public FindSlowTestExtension(long threshold) {
    this.threshold = threshold;
  }

  @Override
  public void beforeTestExecution(ExtensionContext context) {
    Store store = getStore(context);
    store.put(START_TIME, System.currentTimeMillis());
  }

  private Store getStore(ExtensionContext context) {
    String testClassName = context.getRequiredTestClass().getName();
    String testMethodName = context.getRequiredTestMethod().getName();
    return context.getStore(Namespace.create(testClassName, testMethodName));
  }

  @Override
  public void afterTestExecution(ExtensionContext context) {
    Method testMethod = context.getRequiredTestMethod();
    SlowTest annotation = testMethod.getAnnotation(SlowTest.class);

    Store store = getStore(context);
    long startTime = store.remove(START_TIME, long.class);
    long duration = System.currentTimeMillis() - startTime;

    if (duration > threshold && annotation == null)
      System.out.printf("Consider mark method [%s] with @SlowTest", context.getRequiredTestMethod().getName());
  }
}
