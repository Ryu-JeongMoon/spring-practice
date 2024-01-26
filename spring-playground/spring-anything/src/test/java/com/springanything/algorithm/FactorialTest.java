package com.springanything.algorithm;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 2, jvmArgs = { "-Xms4G", "-Xmx4G" })
public class FactorialTest {

  static int facto(int n) {
    return n == 1
      ? 1
      : n * facto(n - 1);
  }

  static int factoIter(int n) {
    int result = 1;
    for (int i = 1; i <= n; i++) {
      result *= i;
    }
    return result;
  }

  @Benchmark
  public void testFacto() {
    log.trace("{}", facto(15));
  }

  @Benchmark
  public void testFactoIter() {
    log.trace("{}", factoIter(15));
  }
}
