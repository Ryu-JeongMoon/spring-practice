package com.springanything.jmh;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime) // 벤치마크 대상 메소드를 실행하는 데 걸린 평균 시간 측정
@OutputTimeUnit(TimeUnit.MILLISECONDS) // 벤치마크 결과를 밀리초 단위로 출력
@Fork(value = 2, jvmArgs = { "-Xms4G", "-Xmx4G" }) // 4Gb의 힙 공간을 제공한 환경에서 두 번 벤치마크를 수행해 결과의 신뢰성 확보
public class CompletableFutureTest {

  // Result "com.springanything.jmh.CompletableFutureTest.streamWithAddAll":
  //   0.138 ±(99.9%) 0.008 ms/op [Average]
  //   (min, avg, max) = (0.130, 0.138, 0.149), stdev = 0.005
  //   CI (99.9%): [0.130, 0.145] (assumes normal distribution)
  //
  //
  // # Run complete. Total time: 00:03:21
  //
  // REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
  // why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
  // experiments, perform baseline and negative tests that provide experimental control, make sure
  // the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
  // Do not assume the numbers tell you what you want them to tell.
  //
  // NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
  // extra caution when trusting the results, look into the generated code to check the benchmark still
  // works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
  // different JVMs are already problematic, the performance difference caused by different Blackhole
  // modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.
  //
  // Benchmark                               Mode  Cnt  Score   Error  Units
  // CompletableFutureTest.streamWithAddAll  avgt   10  0.138 ± 0.008  ms/op
  @Benchmark
  public void streamWithAddAll() {
    List<CompletableFuture<Void>> stream1 = new java.util.ArrayList<>(IntStream.range(0, 10)
      .mapToObj(estimate -> CompletableFuture.runAsync(() -> System.out.println("estimate = " + estimate)))
      .toList());

    List<CompletableFuture<Void>> stream2 = IntStream.range(11, 20)
      .mapToObj(estimate -> CompletableFuture.runAsync(() -> System.out.println("estimate = " + estimate)))
      .toList();

    stream1.addAll(stream2);

    CompletableFuture.allOf(stream1.toArray(new CompletableFuture[0]))
      .thenRun(() -> System.out.println("all done"))
      .join();
  }

  // Result "com.springanything.jmh.CompletableFutureTest.streamEach":
  //   0.163 ±(99.9%) 0.009 ms/op [Average]
  //   (min, avg, max) = (0.154, 0.163, 0.169), stdev = 0.006
  //   CI (99.9%): [0.154, 0.172] (assumes normal distribution)
  //
  //
  // # Run complete. Total time: 00:03:21
  //
  // REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
  // why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
  // experiments, perform baseline and negative tests that provide experimental control, make sure
  // the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
  // Do not assume the numbers tell you what you want them to tell.
  //
  // NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
  // extra caution when trusting the results, look into the generated code to check the benchmark still
  // works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
  // different JVMs are already problematic, the performance difference caused by different Blackhole
  // modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.
  //
  // Benchmark                         Mode  Cnt  Score   Error  Units
  // CompletableFutureTest.streamEach  avgt   10  0.163 ± 0.009  ms/op
  @Benchmark
  public void streamEach() {
    List<CompletableFuture<Void>> stream1 = new java.util.ArrayList<>(IntStream.range(0, 10)
      .mapToObj(estimate -> CompletableFuture.runAsync(() -> System.out.println("estimate = " + estimate)))
      .toList());

    CompletableFuture.allOf(stream1.toArray(new CompletableFuture[0]))
      .thenRun(() -> System.out.println("all done"))
      .join();

    List<CompletableFuture<Void>> stream2 = IntStream.range(11, 20)
      .mapToObj(estimate -> CompletableFuture.runAsync(() -> System.out.println("estimate = " + estimate)))
      .toList();

    CompletableFuture.allOf(stream2.toArray(new CompletableFuture[0]))
      .thenRun(() -> System.out.println("all done"))
      .join();
  }
}
