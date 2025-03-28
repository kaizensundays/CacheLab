package com.kaizensundays.eta.cache

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Warmup
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.TimeUnit.NANOSECONDS
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.math.ln

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(NANOSECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = SECONDS)
@Measurement(iterations = 1, time = 1, timeUnit = SECONDS)
@Fork(1)
@State(Scope.Benchmark)
open class KotlinBench {

    @Param("100", "1000", "10000")
    var size = 0

    private val x = Math.PI

    /** Baseline measurement: how much single Math.log costs. */
    @Benchmark
    fun baseline() = ln(x)

    /**
     * Should your benchmark require returning multiple results, use explicit [Blackhole] objects, and
     * sink the values there. (Background: [Blackhole] is just another @State object, bundled with
     * JMH).
     */
    @Benchmark
    fun test(bh: Blackhole) {
        (1..10).forEach { bh.consume(it) }
    }

    /** Returned results are implicitly consumed by Blackholes */
    @Benchmark
    open fun measureRight(): Double {
        // This is correct: the result is being used.
        return ln(x)
    }

    @Benchmark
    fun newByteArray() {
        val bytes = ByteArray(size)
        bytes[size - 1] = 10
    }
}
