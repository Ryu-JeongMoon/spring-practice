package com.springanything.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class CompareTimer {

  public static final int INITIAL_CAPACITY = 1_000_000;

  @Benchmark
  public DummyData makeObject() {
    return new DummyData(new HashMap<>(INITIAL_CAPACITY), new ArrayList<>(INITIAL_CAPACITY));
  }

  private record DummyData(Map<String, String> map, List<String> list) {

  }
}
