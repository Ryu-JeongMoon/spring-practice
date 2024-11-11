package com.springanything.config;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import jakarta.annotation.Nonnull;

import org.springframework.core.task.TaskDecorator;
import org.springframework.core.task.VirtualThreadTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomVirtualThreadTaskExecutor extends VirtualThreadTaskExecutor {

  private final TaskDecorator taskDecorator;

  public CustomVirtualThreadTaskExecutor(TaskDecorator taskDecorator) {
    this.taskDecorator = taskDecorator;
  }

  @Override
  public void execute(@Nonnull Runnable command) {
    super.execute(taskDecorator.decorate(command));
  }

  @Override
  @Nonnull
  public <T> Future<T> submit(@Nonnull Callable<T> task) {
    return super.submit(() -> {
      Callable<T> decoratedTask = () -> {
        Runnable runnable = taskDecorator.decorate(() -> {
          try {
            task.call();
          } catch (Exception e) {
            log.error("error : ", e);
            throw new RuntimeException(e);
          }
        });
        runnable.run();
        return task.call();
      };
      return decoratedTask.call();
    });
  }

}
