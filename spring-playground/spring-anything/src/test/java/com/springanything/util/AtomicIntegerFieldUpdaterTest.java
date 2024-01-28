package com.springanything.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import org.junit.jupiter.api.Test;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

class AtomicIntegerFieldUpdaterTest {

  // it can be used only with volatile field
  private static final AtomicIntegerFieldUpdater<Activity> ACQUIRED =
    AtomicIntegerFieldUpdater.newUpdater(Activity.class, "acquired");

  @Test
  void update() {
    // given
    Activity activity = Activity.of(5);

    // when
    int result = ACQUIRED.decrementAndGet(activity);

    // then
    assertThat(result).isEqualTo(4);
    assertThat(result).isEqualTo(activity.acquired);
    assertThat(activity.acquired).isEqualTo(4);
  }

  @AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
  private static class Activity {

    volatile int acquired;
  }
}
