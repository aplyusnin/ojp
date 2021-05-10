package ru.nsu.fit.ojp.plyusnin.task_8;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 7)
@Measurement(iterations = 10)
@Threads(2)
@State(Scope.Benchmark)
public class MyBenchmark {

	@Param({"123", "12431234512345", "1274598123579382715", "135679238489a23197582", "1274982174912a", "defiantly not a text"})
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