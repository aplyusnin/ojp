package ru.nsu.fit.ojp.plyusnin.task_8;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3)
@Measurement(iterations = 5)
@Threads(1)
@State(Scope.Benchmark)
public class MyBenchmark {

	@Param({"12432345","12431234512345", "135679238489a23197582", "definitely not a number"})
	public String arg;


	@Benchmark
	public void checkInteger(Blackhole blackhole){
		blackhole.consume(Parsers.parseInteger(arg));
	}

	@Benchmark
	public void checkCharCheck(Blackhole blackhole){
		blackhole.consume(Parsers.parseCharCheck(arg));
	}

	@Benchmark
	public void checkRegex(Blackhole blackhole){
		blackhole.consume(Parsers.parseRegex(arg));
	}
}