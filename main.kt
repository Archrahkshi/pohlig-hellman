import java.math.BigDecimal
import java.math.BigInteger
import kotlin.system.measureNanoTime

fun main() {
    // Example from xntheory
    println("\nEXAMPLE 1")
    val result1 = run(
        p = "181",
        a = "62",
        b = "65",
        q = "3",
        n = 2
    )
    assert(result1.log == BigInteger("5"))

    // Example from https://archive.org/details/handbookofapplie0000mene/page/108/mode/2up
    println("\nEXAMPLE 2")
    val result2 = run(
        p = "251",
        a = "21",
        b = "175",
        q = "5",
        n = 3
    )
    assert(result2.log == BigInteger("72"))

    val timeRatio = result1.time.toBigDecimal() / result2.time.toBigDecimal()
    val complexityRatio = result2.complexity.toBigDecimal() / result1.complexity.toBigDecimal()
    if (timeRatio / complexityRatio == BigDecimal.ONE)
        println("\nTime is aligned with complexity - works as intended")
    else
        println("\nTime is not aligned with complexity")
}

data class Result(
    val log: BigInteger,
    val time: Long,
    val complexity: BigInteger
)

fun run(p: String, a: String, b: String, q: String, n: Int): Result {
    val p1 = BigInteger(p).also { println("\nGroup order (p): $it") }
    val a1 = BigInteger(a).also { println("Logarithm base (a): $it") }
    val b1 = BigInteger(b).also { println("Logarithm argument (b): $it") }
    val q1 = BigInteger(q).also { println("Subgroup order base (q): $it") }
    val n1 = n.also { println("Subgroup order exponent (n): $it") }

    assert(n1 > 1)
    assert(a1.modPow(q1.modPow(n1.toBigInteger(), p1), p1) == BigInteger.ONE) // ordₚ(a) = qⁿ

    val log: BigInteger
    val time = measureNanoTime {
        log = discreteLog(p1, a1, b1, q1, n1).also { println("\nDiscrete logarithm: $it\n") }
    }.also { println("Elapsed time: $it ns") }

    return Result(
        log,
        time,
        calculateComplexity(q1, n1).also { println("n²log₂q + n√q = $it\n") }
    )
}

fun calculateComplexity(q: BigInteger, n1: Int): BigInteger {
    val n = n1.toBigInteger()
    return n.pow(2) * q.bitLength().toBigInteger() + n * q.sqrtFloor() // n²log₂q + n√q
}
